package com.vk.flowable.mgt.service.impl;

import com.google.common.collect.Lists;
import com.vk.flowable.common.utils.BeanMapper;
import com.vk.flowable.mgt.domain.HistoricProcessInstanceInfo;
import com.vk.flowable.mgt.domain.PageBuilder;
import com.vk.flowable.mgt.domain.PageInfo;
import com.vk.flowable.mgt.domain.ProcessInstanceInfo;
import com.vk.flowable.mgt.service.ProcessInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.runtime.ProcessInstanceQuery;
import org.flowable.image.ProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zml on 2019/5/30.
 */
@Slf4j
@Service
public class ProcessInfoServiceImpl implements ProcessInfoService {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ProcessDiagramGenerator processDiagramGenerator;

    @Autowired
    private ProcessEngine processEngine;

    @Override
    public PageInfo<HistoricProcessInstanceInfo> getFinishedProcessList(Integer limit, Integer offset) {
        HistoricProcessInstanceQuery historicQuery = historyService.createHistoricProcessInstanceQuery().finished();
        long totalSize = historicQuery.count();
        List<HistoricProcessInstance> list = historicQuery.orderByProcessInstanceEndTime().desc().listPage(limit, offset);
        if(CollectionUtils.isEmpty(list)) {
            return PageBuilder.buildEmpty();
        }
        List<HistoricProcessInstanceInfo> result = list.stream().map(historicProcessInstance -> {
            // todo 验证bean转换
            HistoricProcessInstanceInfo processInfo = BeanMapper.map(historicProcessInstance, HistoricProcessInstanceInfo.class);
            ProcessDefinition processDefinition = getProcessDefinition(historicProcessInstance.getProcessDefinitionId());
            processInfo.setDeleteReason(historicProcessInstance.getDeleteReason());
            processInfo.setVersion(processDefinition.getVersion());
            return processInfo;
        }).collect(Collectors.toList());
        return PageBuilder.buildPage(totalSize, result);
    }

    @Override
    public PageInfo<ProcessInstanceInfo> getRunningProcessList(Integer limit, Integer offset) {
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        long totalSize = processInstanceQuery.count();
        List<ProcessInstance> list = processInstanceQuery.orderByProcessInstanceId().desc().listPage(limit, offset);
        List<ProcessInstanceInfo> result = BeanMapper.mapList(list, ProcessInstanceInfo.class);
        return PageBuilder.buildPage(totalSize, result);
    }

    @Override
    public ProcessDefinition getProcessDefinition(String processDefinitionId) {
        if (StringUtils.isBlank(processDefinitionId)) {
            return null;
        }
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        log.info("获取流程定义信息 processDefinitionId:{}, 结果:{}", processDefinition, processDefinition);
        return processDefinition;
    }

    @Override
    public InputStream getProcessDiagram(String processInstanceId) {
        if(StringUtils.isBlank(processInstanceId)) {
            return null;
        }
        List<String> activityIds = runtimeService.getActiveActivityIds(processInstanceId);
        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        InputStream inputStream = processDiagramGenerator.generateDiagram(bpmnModel, "png", activityIds, Lists.newArrayList(), engconf.getActivityFontName(), engconf.getLabelFontName(), engconf.getAnnotationFontName(), engconf.getClassLoader(), 1.0, false);
        return inputStream;
    }

    @Override
    public void activateProcessInstance(String processInstanceId) {
        runtimeService.activateProcessInstanceById(processInstanceId);

    }

    @Override
    public void suspendProcessInstance(String processInstanceId) {
        runtimeService.suspendProcessInstanceById(processInstanceId);
    }

}
