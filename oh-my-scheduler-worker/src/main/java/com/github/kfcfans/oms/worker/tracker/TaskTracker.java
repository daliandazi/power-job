package com.github.kfcfans.oms.worker.tracker;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import com.github.kfcfans.common.ExecuteType;
import com.github.kfcfans.common.JobInstanceStatus;
import com.github.kfcfans.common.request.TaskTrackerReportInstanceStatusReq;
import com.github.kfcfans.oms.worker.OhMyWorker;
import com.github.kfcfans.oms.worker.common.constants.AkkaConstant;
import com.github.kfcfans.oms.worker.common.constants.CommonSJ;
import com.github.kfcfans.oms.worker.common.constants.TaskConstant;
import com.github.kfcfans.oms.worker.common.constants.TaskStatus;
import com.github.kfcfans.oms.worker.common.utils.AkkaUtils;
import com.github.kfcfans.oms.worker.common.utils.NetUtils;
import com.github.kfcfans.oms.worker.persistence.TaskDO;
import com.github.kfcfans.oms.worker.persistence.TaskPersistenceService;
import com.github.kfcfans.oms.worker.pojo.model.JobInstanceInfo;
import com.github.kfcfans.oms.worker.pojo.request.TaskTrackerStartTaskReq;
import com.github.kfcfans.oms.worker.pojo.request.WorkerReportTaskStatusReq;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 负责管理 JobInstance 的运行，主要包括任务的派发（MR可能存在大量的任务）和状态的更新
 *
 * @author tjq
 * @since 2020/3/17
 */
@Slf4j
public abstract class TaskTracker {

    // 任务实例信息
    protected JobInstanceInfo jobInstanceInfo;
    protected ActorRef taskTrackerActorRef;

    protected List<String> allWorkerAddress;

    protected TaskPersistenceService taskPersistenceService;
    protected ScheduledExecutorService scheduledPool;

    protected AtomicBoolean finished = new AtomicBoolean(false);

    public TaskTracker(JobInstanceInfo jobInstanceInfo, ActorRef taskTrackerActorRef) {

        this.jobInstanceInfo = jobInstanceInfo;
        this.taskTrackerActorRef = taskTrackerActorRef;
        this.taskPersistenceService = TaskPersistenceService.INSTANCE;

        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("TaskTrackerTimingPool-%s").build();
        this.scheduledPool = Executors.newScheduledThreadPool(2, factory);

        allWorkerAddress = CommonSJ.commaSplitter.splitToList(jobInstanceInfo.getAllWorkerAddress());

        // 持久化根任务
        persistenceRootTask();

        // 定时任务1：任务派发
        scheduledPool.scheduleWithFixedDelay(new DispatcherRunnable(), 0, 5, TimeUnit.SECONDS);

        // 定时任务2：状态检查
        scheduledPool.scheduleWithFixedDelay(new StatusCheckRunnable(), 10, 10, TimeUnit.SECONDS);
    }


    /**
     * 分发任务
     */
    public abstract void dispatch();

    public void updateTaskStatus(WorkerReportTaskStatusReq req) {
        TaskStatus taskStatus = TaskStatus.of(req.getStatus());

        // 持久化，失败则重试一次（本地数据库操作几乎可以认为可靠...吧...）
        boolean updateResult = taskPersistenceService.updateTaskStatus(req.getInstanceId(), req.getTaskId(), taskStatus);
        if (!updateResult) {
            try {
                Thread.sleep(100);
                taskPersistenceService.updateTaskStatus(req.getInstanceId(), req.getTaskId(), taskStatus);
            }catch (Exception ignore) {
            }
        }
    }

    public boolean finished() {
        return finished.get();
    }

    /**
     * 持久化根任务，只有完成持久化才能视为任务开始running（先持久化，再报告server）
     */
    private void persistenceRootTask() {

        ExecuteType executeType = ExecuteType.valueOf(jobInstanceInfo.getExecuteType());
        boolean persistenceResult;

        // 单机、MR模型下，根任务模型本机直接执行（JobTracker一般为负载最小的机器，且MR的根任务通常伴随着 map 操作，本机执行可以有效减少网络I/O开销）
        if (executeType != ExecuteType.BROADCAST) {
            TaskDO rootTask = new TaskDO();
            rootTask.setStatus(TaskStatus.WAITING_DISPATCH.getValue());
            rootTask.setJobId(jobInstanceInfo.getJobId());
            rootTask.setInstanceId(jobInstanceInfo.getInstanceId());
            rootTask.setTaskId(TaskConstant.ROOT_TASK_ID);
            rootTask.setFailedCnt(0);
            rootTask.setAddress(NetUtils.getLocalHost());
            rootTask.setTaskName(TaskConstant.ROOT_TASK_NAME);
            rootTask.setCreatedTime(System.currentTimeMillis());
            rootTask.setCreatedTime(System.currentTimeMillis());

            persistenceResult = taskPersistenceService.save(rootTask);
        }else {
            List<TaskDO> taskList = Lists.newLinkedList();
            List<String> addrList = CommonSJ.commaSplitter.splitToList(jobInstanceInfo.getAllWorkerAddress());
            for (int i = 0; i < addrList.size(); i++) {
                TaskDO task = new TaskDO();
                task.setStatus(TaskStatus.WAITING_DISPATCH.getValue());
                task.setJobId(jobInstanceInfo.getJobId());
                task.setInstanceId(jobInstanceInfo.getInstanceId());
                task.setTaskId(String.valueOf(i));
                task.setAddress(addrList.get(i));
                task.setFailedCnt(0);
                task.setTaskName(TaskConstant.ROOT_TASK_NAME);
                task.setCreatedTime(System.currentTimeMillis());
                task.setCreatedTime(System.currentTimeMillis());

                taskList.add(task);
            }
            persistenceResult = taskPersistenceService.batchSave(taskList);
        }

        if (!persistenceResult) {
            throw new RuntimeException("create root task failed.");
        }
    }


    public void destroy() {
        scheduledPool.shutdown();
    }

    /**
     * 定时扫描数据库中的task（出于内存占用量考虑，每次最多获取100个），并将需要执行的任务派发出去
     */
    private class DispatcherRunnable implements Runnable {

        @Override
        public void run() {
            taskPersistenceService.getNeedDispatchTask(jobInstanceInfo.getInstanceId()).forEach(task -> {
                try {
                    // 构造 worker 执行请求
                    TaskTrackerStartTaskReq req = new TaskTrackerStartTaskReq(jobInstanceInfo, task);

                    // 构造 akka 可访问节点路径
                    String targetIP = task.getAddress();
                    if (StringUtils.isEmpty(targetIP)) {
                        targetIP = allWorkerAddress.get(ThreadLocalRandom.current().nextInt(allWorkerAddress.size()));
                    }
                    String targetPath = AkkaUtils.getAkkaRemotePath(targetIP, AkkaConstant.WORKER_ACTOR_NAME);
                    ActorSelection targetActor = OhMyWorker.actorSystem.actorSelection(targetPath);

                    // 发送请求（Akka的tell是至少投递一次，经实验表明无法投递消息也不会报错...印度啊...）
                    targetActor.tell(req, taskTrackerActorRef);

                    // 更新数据库（如果更新数据库失败，可能导致重复执行，先不处理）
                    taskPersistenceService.updateTaskStatus(task.getInstanceId(), task.getTaskId(), TaskStatus.DISPATCH_SUCCESS_WORKER_UNCHECK);
                }catch (Exception e) {
                    // 调度失败，不修改数据库，下次重新随机派发给 remote actor
                    log.warn("[TaskTracker] dispatch task({}) failed.", task);
                }
            });
        }
    }

    /**
     * 定时检查当前任务的执行状态
     */
    private class StatusCheckRunnable implements Runnable {

        @Override
        public void run() {

            // 1. 查询统计信息
            Map<TaskStatus, Long> status2Num = taskPersistenceService.getTaskStatusStatistics(jobInstanceInfo.getInstanceId());

            long waitingDispatchNum = status2Num.get(TaskStatus.WAITING_DISPATCH);
            long workerUnreceivedNum = status2Num.get(TaskStatus.DISPATCH_SUCCESS_WORKER_UNCHECK);
            long receivedNum = status2Num.get(TaskStatus.RECEIVE_SUCCESS);
            long succeedNum = status2Num.get(TaskStatus.WORKER_PROCESS_SUCCESS);
            long failedNum = status2Num.get(TaskStatus.WORKER_PROCESS_FAILED);

            long finishedNum = succeedNum + failedNum;
            long unfinishedNum = waitingDispatchNum + workerUnreceivedNum + receivedNum;

            log.debug("[TaskTracker] status check result({})", status2Num);

            TaskTrackerReportInstanceStatusReq req = new TaskTrackerReportInstanceStatusReq();

            // 2. 如果未完成任务数为0，上报服务器
            if (unfinishedNum == 0) {
                finished.set(true);

                if (failedNum == 0) {
                    req.setInstanceStatus(JobInstanceStatus.SUCCEED.getValue());
                }else {
                    req.setInstanceStatus(JobInstanceStatus.FAILED.getValue());
                }

                // 特殊处理MapReduce任务(执行reduce)
                // 特殊处理广播任务任务（执行postProcess）
            }else {

            }

        }
    }
}
