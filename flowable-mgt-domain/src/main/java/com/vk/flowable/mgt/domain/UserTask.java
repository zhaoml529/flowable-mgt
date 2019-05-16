package com.vk.flowable.mgt.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created by zml on 2019/5/16.
 */
@Data
@TableName("tb_user_task")
public class UserTask extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String procDefKey;		// com.zml.oa.vacation

    private String procDefName;		// 请假申请

    private String taskDefKey;		// hrAudti

    private String taskName;		// 人事审批

    private String taskType;		// 1.assignee.受理人(唯一) 1.candidateUser候选人(多个) 2.candidateGroup候选组（多个）

    private String candidate_name; 	// 人或候选人或组的名称

    private String candidate_ids;
}
