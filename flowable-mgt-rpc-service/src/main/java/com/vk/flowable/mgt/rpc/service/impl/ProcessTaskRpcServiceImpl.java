package com.vk.flowable.mgt.rpc.service.impl;

import com.vk.flowable.common.utils.BeanMapper;
import com.vk.flowable.mgt.domain.ProcessTask;
import com.vk.flowable.mgt.rpc.api.response.ApiResponse;
import com.vk.flowable.mgt.rpc.api.response.ApiResponseBuilder;
import com.vk.flowable.mgt.rpc.api.dto.ProcessTaskDTO;
import com.vk.flowable.mgt.rpc.api.service.ProcessTaskRpcService;
import com.vk.flowable.mgt.service.ProcessTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zml on 2019/5/16.
 */
@Slf4j
@Service
public class ProcessTaskRpcServiceImpl implements ProcessTaskRpcService {

    @Autowired
    private ProcessTaskService processTaskService;

    @Override
    public ApiResponse<List<ProcessTaskDTO>> findTodoTask(Long userId, Integer limit, Integer offset) {
        List<ProcessTask> list = processTaskService.findTodoTask(userId, limit, offset);
        List<ProcessTaskDTO> dtoList = BeanMapper.mapList(list, ProcessTaskDTO.class);
        return ApiResponseBuilder.buildSuccess(dtoList);
    }

    @Override
    public ApiResponse claimTask(Long userId, String taskId) {
        return null;
    }
}
