package com.vk.flowable.mgt.domain;

import lombok.Data;

import java.util.Date;

/**
 * 流程任务
 * Created by zml on 2019/5/28.
 */
@Data
public class ProcessTask {

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

}
