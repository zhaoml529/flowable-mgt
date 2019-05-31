package com.vk.flowable.mgt.service.impl;

import com.google.common.collect.Lists;
import com.vk.flowable.common.enums.ResponseCode;
import com.vk.flowable.common.exception.BizException;
import com.vk.flowable.mgt.domain.CommentInfo;
import com.vk.flowable.mgt.domain.ProcessTask;
import com.vk.flowable.mgt.service.ProcessInfoService;
import com.vk.flowable.mgt.service.ProcessTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.idm.api.User;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 流程任务相关
 * Created by zml on 2019/5/16.
 */
@Slf4j
@Service
public class ProcessTaskServiceImpl implements ProcessTaskService {

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ProcessInfoService processInfoService;

    @Autowired
    private IdentityService identityService;

    @Override
    public List<ProcessTask> findTodoTask(Long userId, Integer limit, Integer offset) {
        TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(userId.toString());
        List<Task> list = taskQuery.orderByTaskCreateTime().desc().listPage(limit, offset);
        if(CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<ProcessTask> taskList = Lists.newArrayList();
        list.forEach(task -> {
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
            if (Objects.isNull(processInstance)) {
                // 可能有挂起的流程信息
                return;
            }
            ProcessTask processTask = new ProcessTask();
            processTask.setTaskId(task.getId());
            processTask.setTaskName(task.getName());
            processTask.setTaskCreateTime(task.getCreateTime());
            processTask.setTaskDefinitionKey(task.getTaskDefinitionKey());
            processTask.setProcessInstanceId(task.getProcessInstanceId());
            processTask.setSupended(processInstance.isSuspended());
            ProcessDefinition processDefinition = processInfoService.getProcessDefinition(processInstance.getProcessDefinitionId());
            if(Objects.nonNull(processDefinition)) {
                processTask.setProcessDefinitionId(processDefinition.getId());
                processTask.setProcessDefinitionKey(processDefinition.getKey());
                processTask.setProcessDefinitionVersion(processDefinition.getVersion());
            }
            taskList.add(processTask);
        });
        return taskList;
    }

    @Override
    public Task findTaskById(String taskId) {
        if(StringUtils.isBlank(taskId)) {
            return null;
        }
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        return task;
    }

    @Override
    public Task findTaskByProcessInstanceId(String processInstanceId) {
        if(StringUtils.isBlank(processInstanceId)) {
            return null;
        }
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        return task;
    }

    @Override
    public void claimTask(Long userId, String taskId) {
        if(Objects.isNull(userId) || StringUtils.isBlank(taskId)) {
            return;
        }
        // 这个方法最终使用一个ThreadLocal类型的变量进行存储，也就是与当前的线程绑定，所以流程实例启动完毕之后，需要设置为null，防止多线程的时候出问题。
        Authentication.setAuthenticatedUserId(userId.toString());
        taskService.claim(taskId, userId.toString());
        Authentication.setAuthenticatedUserId(null);
        log.info("签收任务成功，userId:{}, taskId:{}", userId, taskId);
    }

    @Override
    public void delegateTask(Long userId, String taskId) {
        if(Objects.isNull(userId) || StringUtils.isBlank(taskId)) {
            return;
        }
        //API: If no owner is set on the task, the owner is set to the current assignee of the task.
        //OWNER_（委托人）：受理人委托其他人操作该TASK的时候，受理人就成了委托人OWNER_，其他人就成了受理人ASSIGNEE_
        //assignee容易理解，主要是owner字段容易误解，owner字段就是用于受理人委托别人操作的时候运用的字段
        taskService.delegateTask(taskId, userId.toString());
        log.info("委派任务成功，userId:{}, taskId:{}", userId, taskId);
    }

    @Override
    public void transferTask(Long userId, String taskId) {
        if(Objects.isNull(userId) || StringUtils.isBlank(taskId)) {
            return;
        }
        Task task = findTaskById(taskId);
        if(Objects.isNull(task)) {
            throw new BizException(ResponseCode.ERROR.code, "当前任务不存在");
        }
        String assign = task.getAssignee(); // 当前任务原受理人
        taskService.setAssignee(taskId, userId.toString()); // 给任务设置新的受理人
        taskService.setOwner(taskId, assign);// 设置当前任务的委托人
        log.info("签收任务成功，userId:{}, taskId:{}", userId, taskId);
    }

    @Override
    public void completeTask(String taskId, String comment, Long userId, Map<String, Object> variables) {
        if(Objects.isNull(taskId)) {
            throw new BizException("taskId不能为空");
        }
        if(Objects.isNull(userId)) {
            throw new BizException("userId不能为空");
        }
        Task task = findTaskById(taskId);
        if(Objects.isNull(task)) {
            throw new BizException("当前任务不存在");
        }
        // 根据任务查询流程实例
        String processInstanceId = task.getProcessInstanceId();
        //评论人的id  一定要写，不然查看的时候会报错，没有用户
        identityService.setAuthenticatedUserId(userId.toString());
        if(StringUtils.isBlank(comment)) {
            comment = "同意";
            taskService.addComment(taskId, processInstanceId, comment);
        }
        if(Objects.equals(task.getDelegationState(), DelegationState.PENDING)) {
            // 完成委派任务
            taskService.resolveTask(taskId, variables);
        }
        // 正常完成任务
        taskService.complete(taskId, variables);
    }

    @Override
    public void revokeTask(String historyTaskId, String processInstanceId) {
        Task oldTask = findTaskById(historyTaskId);
        Task currentTask = findTaskByProcessInstanceId(processInstanceId);
        if(Objects.isNull(currentTask)) {
            throw new BizException("当前任务不存在");
        }
        String currentActivityId = currentTask.getTaskDefinitionKey();
        String oldActivityId = oldTask.getTaskDefinitionKey();
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId(processInstanceId)
                .moveActivityIdTo(currentActivityId, oldActivityId)
                .changeState();
    }

    @Override
    public void moveTo(String currentTaskId, String targetTaskDefinitionKey) {
        Task task = findTaskById(currentTaskId);
        if(Objects.isNull(task)) {
            throw new BizException("当前任务不存在");
        }
        if(StringUtils.isBlank(targetTaskDefinitionKey)) {
            throw new BizException("目标节点不能为空");
        }
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId(task.getProcessInstanceId())
                .moveActivityIdTo(task.getTaskDefinitionKey(), targetTaskDefinitionKey)
                .changeState();
    }

    @Override
    public List<CommentInfo> findComments(String processInstanceId) {
        List<Comment> comments = taskService.getProcessInstanceComments(processInstanceId);
        if(CollectionUtils.isEmpty(comments)) {
            return Collections.emptyList();
        }
        List<CommentInfo> list = comments.stream().map(comment -> {
            User user = identityService.createUserQuery().userId(comment.getUserId()).singleResult();
            CommentInfo commentInfo = new CommentInfo();
            commentInfo.setUserId(comment.getUserId());
            commentInfo.setUserName(user.getDisplayName());
            return commentInfo;
        }).collect(Collectors.toList());
        return list;
    }


}
