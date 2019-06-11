package com.vk.flowable.common.enums;

import java.text.MessageFormat;

/**
 * 错误码
 */
public enum ResponseCode {

    // 错误码只可新增或删除，不可修改
    SUCCESS("0000", "success"),
    ERROR("0001", "error"),

    // 任务相关错误码10开头,编号001递增
    TASK_CLAIM_FAILED("10001", "任务签收失败"),
    ;

    public final String code;

    public final String msg;

    ResponseCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String formatMsg(Object...args) {
        return MessageFormat.format(this.msg, args);
    }

}
