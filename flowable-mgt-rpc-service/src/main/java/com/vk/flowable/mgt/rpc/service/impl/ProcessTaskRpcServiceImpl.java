package com.vk.flowable.mgt.rpc.service.impl;

import com.vk.flowable.common.enums.ResponseCode;
import com.vk.flowable.common.utils.BeanMapper;
import com.vk.flowable.mgt.domain.ProcessTask;
import com.vk.flowable.mgt.rpc.api.request.CompleteTaskParam;
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
            return ApiResponseBuilder.buildError(ResponseCode.ERROR.code, "userId不能为空");
        }
        if(StringUtils.isBlank(taskId)) {
            return ApiResponseBuilder.buildError(ResponseCode.ERROR.code, "taskId不能为空");
        }
        try {
            processTaskService.claimTask(userId, taskId);
            return ApiResponseBuilder.buildSuccess();
        } catch (Exception e) {
            log.error("任务签收失败，参数 userId:{}, taskId:{}, 异常:", userId, taskId, e);
            return ApiResponseBuilder.buildError(ResponseCode.TASK_CLAIM_FAILED.code, ResponseCode.TASK_CLAIM_FAILED.msg);
        }
    }

    @Override
    public ApiResponse delegateTask(Long userId, String taskId) {
        if(Objects.isNull(userId)) {
            return ApiResponseBuilder.buildError(ResponseCode.ERROR.code, "userId不能为空");
        }
        if(StringUtils.isBlank(taskId)) {
            return ApiResponseBuilder.buildError(ResponseCode.ERROR.code, "taskId不能为空");
        }
        try {
            processTaskService.delegateTask(userId, taskId);
            return ApiResponseBuilder.buildSuccess();
        } catch (Exception e) {
            log.error("委派任务失败，参数 userId:{}, taskId:{} 异常:", userId, taskId, e);
            return ApiResponseBuilder.buildError(ResponseCode.TASK_DELEGATE_FAILED.code, ResponseCode.TASK_DELEGATE_FAILED.msg);
        }
    }

    @Override
    public ApiResponse transferTask(Long userId, String taskId) {
        if(Objects.isNull(userId)) {
            return ApiResponseBuilder.buildError(ResponseCode.ERROR.code, "userId不能为空");
        }
        if(StringUtils.isBlank(taskId)) {
            return ApiResponseBuilder.buildError(ResponseCode.ERROR.code, "taskId不能为空");
        }
        try {
            processTaskService.transferTask(userId, taskId);
            return ApiResponseBuilder.buildSuccess();
        } catch (Exception e) {
            log.error("转办任务失败，参数 userId:{}, taskId:{} 异常:", userId, taskId, e);
            return ApiResponseBuilder.buildError(ResponseCode.TASK_TRANSFER_FAILED.code, ResponseCode.TASK_TRANSFER_FAILED.msg);
        }
    }

    @Override
    public ApiResponse completeTask(CompleteTaskParam completeTaskParam) {
        if(Objects.isNull(completeTaskParam)) {
            return ApiResponseBuilder.buildError(ResponseCode.ERROR.code, "缺少必要参数");
        }
        if(Objects.isNull(completeTaskParam.getUserId())) {
            return ApiResponseBuilder.buildError(ResponseCode.ERROR.code, "userId不能为空");
        }
        if(StringUtils.isBlank(completeTaskParam.getTaskId())) {
            return ApiResponseBuilder.buildError(ResponseCode.ERROR.code, "taskId不能为空");
        }
        try {
            processTaskService.completeTask(completeTaskParam.getTaskId(), completeTaskParam.getComment(), completeTaskParam.getUserId(), completeTaskParam.getVariables());
            return ApiResponseBuilder.buildSuccess();
        } catch (Exception e) {
            log.error("完成任务失败，参数 completeTaskParam:{} 异常:", completeTaskParam, e);
            return ApiResponseBuilder.buildError(ResponseCode.TASK_COMPLETE_FAILED.code, ResponseCode.TASK_COMPLETE_FAILED.msg);
        }
    }
}
