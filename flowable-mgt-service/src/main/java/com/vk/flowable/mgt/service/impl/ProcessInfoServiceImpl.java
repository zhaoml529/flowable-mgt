package com.vk.flowable.mgt.service.impl;

import com.vk.flowable.common.utils.BeanMapper;
import com.vk.flowable.mgt.domain.HistoricProcessInfo;
import com.vk.flowable.mgt.domain.PageBuilder;
import com.vk.flowable.mgt.domain.PageInfo;
import com.vk.flowable.mgt.service.ProcessInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

    @Override
    public PageInfo<HistoricProcessInfo> findFinishedProcessInstances(Integer limit, Integer offset) {
        HistoricProcessInstanceQuery historicQuery = historyService.createHistoricProcessInstanceQuery().finished();
        long totalSize = historicQuery.count();
        List<HistoricProcessInstance> list = historicQuery.orderByProcessInstanceEndTime().desc().listPage(limit, offset);
        if(CollectionUtils.isEmpty(list)) {
            return PageBuilder.buildEmpty();
        }
        List<HistoricProcessInfo> result = list.stream().map(historicProcessInstance -> {
            // todo 验证bean转换
            HistoricProcessInfo processInfo = BeanMapper.map(historicProcessInstance, HistoricProcessInfo.class);
            ProcessDefinition processDefinition = getProcessDefinition(historicProcessInstance.getProcessDefinitionId());
            processInfo.setDeleteReason(historicProcessInstance.getDeleteReason());
            processInfo.setVersion(processDefinition.getVersion());
            return processInfo;
        }).collect(Collectors.toList());
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
}
