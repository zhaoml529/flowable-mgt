package com.vk.flowable.mgt.service;

import com.vk.flowable.mgt.domain.ProcessTask;

import java.util.List;

/**
 * Created by zml on 2019/5/16.
 */
public interface ProcessTaskService {

    /**
     * 查询代办任务列表
     * @param userId
     * @param limit
     * @param offset
     * @return
     */
    List<ProcessTask> findTodoTask(Long userId, Integer limit, Integer offset);

    /**
     * 签收任务
     * @param userId
     * @param taskId
     */
    void claimTask(Long userId, String taskId);

    /**
     * 委派任务
     * @param userId
     * @param taskId
     */
    void delegateTask(Long userId, String taskId);


    /**
     * 转办任务
     * @param userId
     * @param taskId
     */
    void transferTask(Long userId, String taskId);

}
