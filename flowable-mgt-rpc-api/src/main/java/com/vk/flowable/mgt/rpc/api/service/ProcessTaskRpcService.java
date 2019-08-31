package com.vk.flowable.mgt.rpc.api.service;

import com.vk.flowable.mgt.rpc.api.request.CompleteTaskParam;
import com.vk.flowable.mgt.rpc.api.response.ApiResponse;
import com.vk.flowable.mgt.rpc.api.dto.ProcessTaskDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by zml on 2019/5/16.
 */
public interface ProcessTaskRpcService {

    /**
     * 分页查询用户待办任务列表
     * @param userId
     * @param limit
     * @param offset
     * @return
     */
    ApiResponse<List<ProcessTaskDTO>> findTodoTask(Long userId, Integer limit, Integer offset);

    /**
     * 签收任务
     * @param userId
     * @param taskId
     * @return
     */
    ApiResponse claimTask(Long userId, String taskId);

    /**
     * 委派任务
     * @param userId
     * @param taskId
     * @return
     */
    ApiResponse delegateTask(Long userId, String taskId);

    /**
     * 转办任务
     * @param userId
     * @param taskId
     * @return
     */
    ApiResponse transferTask(Long userId, String taskId);

    /**
     * 完成任务
     * @param completeTaskParam
     * @return
     */
    ApiResponse completeTask(CompleteTaskParam completeTaskParam);

}
