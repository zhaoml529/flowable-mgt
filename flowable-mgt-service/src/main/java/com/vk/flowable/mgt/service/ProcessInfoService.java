package com.vk.flowable.mgt.service;


import com.vk.flowable.mgt.domain.PageInfo;
import com.vk.flowable.mgt.domain.HistoricProcessInstanceInfo;
import com.vk.flowable.mgt.domain.ProcessInstanceInfo;
import org.flowable.engine.repository.ProcessDefinition;

import java.io.InputStream;

/**
 * Created by zml on 2019/5/30.
 */
public interface ProcessInfoService {

    /**
     * 获取已结束的流程列表
     * @param limit
     * @param offset
     * @return
     */
    PageInfo<HistoricProcessInstanceInfo> getFinishedProcessList(Integer limit, Integer offset);

    /**
     * 获取运行中的流程列表
     * @param limit
     * @param offset
     * @return
     */
    PageInfo<ProcessInstanceInfo> getRunningProcessList(Integer limit, Integer offset);

    /**
     * 获取流程定义
     * @param processDefinitionId
     * @return
     */
    ProcessDefinition getProcessDefinition(String processDefinitionId);

    /**
     * 获取流程图，带流程跟踪
     * @param processInstanceId
     * @return
     */
    InputStream getProcessDiagram(String processInstanceId);

    /**
     * 激活流程实例
     * @param processInstanceId
     */
    void activateProcessInstance(String processInstanceId);

    /**
     * 挂起流程实例
     * @param processInstanceId
     */
    void suspendProcessInstance(String processInstanceId);

}
