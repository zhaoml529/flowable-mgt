package com.vk.flowable.mgt.service;


import com.vk.flowable.mgt.domain.PageInfo;
import com.vk.flowable.mgt.domain.HistoricProcessInfo;
import org.flowable.engine.repository.ProcessDefinition;

/**
 * Created by zml on 2019/5/30.
 */
public interface ProcessInfoService {

    PageInfo<HistoricProcessInfo> findFinishedProcessInstances(Integer limit, Integer offset);

    /**
     * 获取流程定义
     * @param processDefinitionId
     * @return
     */
    ProcessDefinition getProcessDefinition(String processDefinitionId);
}
