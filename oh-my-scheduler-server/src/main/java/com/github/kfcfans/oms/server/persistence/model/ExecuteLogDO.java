package com.github.kfcfans.oms.server.persistence.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 任务运行日志表
 *
 * @author tjq
 * @since 2020/3/30
 */
@Data
@Entity
@Table(name = "execute_log", indexes = {@Index(columnList = "jobId")})
public class ExecuteLogDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 任务ID
    private Long jobId;
    // 任务所属应用的ID，冗余提高查询效率
    private Long appId;
    // 任务实例ID
    private Long instanceId;
    /**
     * 任务状态 {@link com.github.kfcfans.common.InstanceStatus}
     */
    private int status;
    // 执行结果
    private String result;
    // 预计触发时间
    private Long expectedTriggerTime;
    // 实际触发时间
    private Long actualTriggerTime;
    // 结束时间
    private Long finishedTime;

    // 总共执行的次数（用于重试判断）
    private Long runningTimes;

    private Date gmtCreate;
    private Date gmtModified;

}
