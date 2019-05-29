package com.vk.flowable.mgt.rpc.api.response;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zml on 2019/5/16.
 */
public class ProcessTaskDTO implements Serializable {

    private static final long serialVersionUID = 4480253775380280195L;

    private Long businessId;    // 业务主键

    private String taskId;      // 任务id

    private String taskName;    // 任务名称

    private Date taskCreateTime;// 任务创建时间

    private String taskDefinitionKey;   // 任务定义

    private String processInstanceId;   // 流程实例id

    private boolean supended;           // 流程实例状态是否挂起(true:挂起 false:没挂起)

    private String processDefinitionId;     // 流程定义id

    private String processDefinitionKey;    // 流程定义key

    private int processDefinitionVersion;   // 流程定义版本号

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getTaskCreateTime() {
        return taskCreateTime;
    }

    public void setTaskCreateTime(Date taskCreateTime) {
        this.taskCreateTime = taskCreateTime;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public boolean isSupended() {
        return supended;
    }

    public void setSupended(boolean supended) {
        this.supended = supended;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public int getProcessDefinitionVersion() {
        return processDefinitionVersion;
    }

    public void setProcessDefinitionVersion(int processDefinitionVersion) {
        this.processDefinitionVersion = processDefinitionVersion;
    }

    @Override
    public String toString() {
        return "ProcessTaskDTO{" +
                "businessId=" + businessId +
                ", taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", taskCreateTime=" + taskCreateTime +
                ", taskDefinitionKey='" + taskDefinitionKey + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", supended=" + supended +
                ", processDefinitionId='" + processDefinitionId + '\'' +
                ", processDefinitionKey='" + processDefinitionKey + '\'' +
                ", processDefinitionVersion=" + processDefinitionVersion +
                '}';
    }
}
