package com.vk.flowable.mgt.rpc.service.impl;

import com.vk.flowable.common.enums.ResponseCode;
import com.vk.flowable.common.utils.BeanMapper;
import com.vk.flowable.mgt.domain.ProcessTask;
import com.vk.flowable.mgt.rpc.api.response.ApiResponse;
import com.vk.flowable.mgt.rpc.api.response.ApiResponseBuilder;
import com.vk.flowable.mgt.rpc.api.dto.ProcessTaskDTO;
import com.vk.flowable.mgt.rpc.api.service.ProcessTaskRpcService;
import com.vk.flowable.mgt.service.ProcessTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        List<ProcessTask> list = processTaskService.getTodoTask(userId, limit, offset);
        List<ProcessTaskDTO> dtoList = BeanMapper.mapList(list, ProcessTaskDTO.class);
        return ApiResponseBuilder.buildSuccess(dtoList);
    }

    @Override
    public ApiResponse claimTask(Long userId, String taskId) {
        if(Objects.isNull(userId)) {
            ApiResponseBuilder.buildError(ResponseCode.ERROR.code, "userId不能为空");
        }
        if(StringUtils.isBlank(taskId)) {
            ApiResponseBuilder.buildError(ResponseCode.ERROR.code, "taskId不能为空");
        }
        try {
            processTaskService.claimTask(userId, taskId);
            return ApiResponseBuilder.buildSuccess();
        } catch (Exception e) {
            log.error("任务签收失败，参数 userId:{}, taskId:{}, 异常:", userId, taskId, e);
            return ApiResponseBuilder.buildError(ResponseCode.TASK_CLAIM_FAILED.code, ResponseCode.TASK_CLAIM_FAILED.msg);
        }
    }
}
