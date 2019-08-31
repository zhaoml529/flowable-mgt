package com.vk.flowable.mgt.rpc.api.request;

import java.util.Map;

/**
 * 完成任务参数
 * Created by zml on 2019/8/31.
 */
public class CompleteTaskParam {
    private String taskId;      // 任务id
    private String comment;     // 评论
    private Long userId;        // 用户id
    private Map<String, Object> variables;  // 业务参数

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }
}
