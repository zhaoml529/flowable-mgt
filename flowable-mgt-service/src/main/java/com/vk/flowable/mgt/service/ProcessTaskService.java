package com.vk.flowable.mgt.service;

import com.vk.flowable.mgt.domain.CommentInfo;
import com.vk.flowable.mgt.domain.ProcessTask;
import org.flowable.task.api.Task;

import java.util.List;
import java.util.Map;

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
    List<ProcessTask> getTodoTask(Long userId, Integer limit, Integer offset);

    /**
     * 根据id查询任务信息
     * @param taskId
     * @return
     */
    Task getTaskById(String taskId);

    /**
     * 根据processInstanceId查询任务
     * @param processInstanceId
     * @return
     */
    Task getTaskByProcessInstanceId(String processInstanceId);

    /**
     * 启动任务
     * @param userId
     * @param processDefinitionId
     * @param businessKey
     * @param variables
     * @return processInstanceId
     */
    String startTask(Long userId, String processDefinitionId, String businessKey, Map<String, Object> variables);

    /**
     * 签收任务
     * @param userId
     * @param taskId
     */
    Boolean claimTask(Long userId, String taskId);

    /**
     * 委派任务
     * @param userId
     * @param taskId
     */
    Boolean delegateTask(Long userId, String taskId);


    /**
     * 转办任务
     * @param userId
     * @param taskId
     */
    Boolean transferTask(Long userId, String taskId);

    /**
     * 完成任务
     * @param taskId
     * @param comment
     * @param userId
     * @param variables
     */
    Boolean completeTask(String taskId, String comment, Long userId, Map<String, Object> variables);

    /**
     * 撤回任务
     * @param historyTaskId
     * @param processInstanceId
     * @return
     */
    Boolean revokeTask(String historyTaskId, String processInstanceId);

    /**
     * 跳转（包括回退和向前）至指定活动节点
     * @param currentTaskId
     * @param targetTaskDefinitionKey
     */
    Boolean moveTo(String currentTaskId, String targetTaskDefinitionKey);

    /**
     * 根据processInstanceId查询评论信息
     * @param processInstanceId
     * @return
     */
    List<CommentInfo> findComments(String processInstanceId);

}
