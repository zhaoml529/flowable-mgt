package com.vk.flowable.mgt.rpc.api.service;

import com.vk.flowable.mgt.rpc.api.response.ApiResponse;
import com.vk.flowable.mgt.rpc.api.response.ProcessTaskDTO;

import java.util.List;

/**
 * Created by zml on 2019/5/16.
 */
public interface ProcessTaskRpcService {

    ApiResponse<List<ProcessTaskDTO>> findTodoTask(Long userId, Integer limit, Integer offset);
}
