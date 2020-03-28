package com.github.kfcfans.common.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 服务端调度任务请求（一次任务处理的入口）
 *
 * @author tjq
 * @since 2020/3/17
 */
@Data
public class ServerScheduleJobReq implements Serializable {

    // 调度的服务器地址，默认通讯目标
    private String serverAddress;
    // 可用处理器地址，可能多值，逗号分隔
    private String allWorkerAddress;

    /* *********************** 任务相关属性 *********************** */

    /**
     * 基础信息
     */
    private String jobId;
    private String instanceId;

    /**
     * 任务执行处理器信息
     */
    // 任务执行类型，单机、广播、MR
    private String executeType;
    // 处理器类型（JavaBean、Jar、脚本等）
    private String processorType;
    // 处理器信息
    private String processorInfo;


    /**
     * 超时时间
     */
    // 整个任务的总体超时时间
    private long instanceTimeoutMS;
    // Task的超时时间
    private long taskTimeoutMS;

    /**
     * 任务运行参数
     */
    // 任务级别的参数，相当于类的static变量
    private String jobParams;
    // 实例级别的参数，相当于类的普通变量
    private String instanceParams;

    // 每台机器的处理线程数上限
    private int threadConcurrency;
    // 子任务重试次数（任务本身的重试机制由server控制）
    private int taskRetryNum;



}
