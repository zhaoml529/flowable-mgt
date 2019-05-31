package com.vk.flowable.mgt.domain;

import lombok.Data;

/**
 * Created by zml on 2019/5/31.
 */
@Data
public class ProcessInstanceInfo {

    private String id;

    private String processInstanceId; 		// 流程实例id

    private String processDefinitionId; 	// 流程定义id

    private String activityId; 				// 流程节点id

    private Boolean suspended;				// 挂起/激活

    private String taskName;				// 当前节点名称

}
