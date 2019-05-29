package com.vk.flowable.mgt.service.impl;

import com.google.common.collect.Lists;
import com.vk.flowable.mgt.domain.ProcessTask;
import com.vk.flowable.mgt.service.ProcessTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    private RepositoryService repositoryService;

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
            ProcessDefinition processDefinition = getProcessDefinition(processInstance.getProcessDefinitionId());
            if(Objects.nonNull(processDefinition)) {
                processTask.setProcessDefinitionId(processDefinition.getId());
                processTask.setProcessDefinitionKey(processDefinition.getKey());
                processTask.setProcessDefinitionVersion(processDefinition.getVersion());
            }
            taskList.add(processTask);
        });
        return taskList;
    }

    private ProcessDefinition getProcessDefinition(String processDefinitionId) {
        if (StringUtils.isBlank(processDefinitionId)) {
            return null;
        }
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        log.info("获取流程定义信息 processDefinitionId:{}, 结果:{}", processDefinition, processDefinition);
        return processDefinition;
    }

}
