package com.github.kfcfans.oms.processors;

import com.github.kfcfans.oms.worker.sdk.ProcessResult;
import com.github.kfcfans.oms.worker.sdk.TaskContext;
import com.github.kfcfans.oms.worker.sdk.api.MapReduceProcessor;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 测试 MapReduce 处理器
 *
 * @author tjq
 * @since 2020/3/24
 */
public class TestMapReduceProcessor extends MapReduceProcessor {

    @Override
    public ProcessResult reduce(TaskContext taskContext, Map<String, String> taskId2Result) {
        System.out.println("============== TestMapReduceProcessor#reduce ==============");
        System.out.println("taskContext:" + taskContext);
        System.out.println("taskId2Result:" + taskId2Result);
        return new ProcessResult(true, "REDUCE_SUCCESS");
    }

    @Override
    public ProcessResult process(TaskContext context) throws Exception {
        System.out.println("============== TestMapReduceProcessor#process ==============");
        System.out.println("isRootTask:" + isRootTask());
        System.out.println("TaskContext:" + context.toString());

        if (isRootTask()) {
            System.out.println("start to map");
            List<TestSubTask> subTasks = Lists.newLinkedList();
            for (int i = 0; i < 100; i++) {
                subTasks.add(new TestSubTask("name" + i, i));
            }
            ProcessResult mapResult = map(subTasks, "MAP_TEST_TASK");
            System.out.println("map result = " + mapResult);
            return new ProcessResult(true, "MAP_SUCCESS");
        }else {
            System.out.println("start to process");
            System.out.println(context.getSubTask());
            return new ProcessResult(true, "PROCESS_SUCCESS");
        }

    }

    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    private static class TestSubTask implements Serializable {
        private String name;
        private int age;
    }

    @Override
    public void init() throws Exception {
        System.out.println("============== TestMapReduceProcessor#init ==============");
    }
}
