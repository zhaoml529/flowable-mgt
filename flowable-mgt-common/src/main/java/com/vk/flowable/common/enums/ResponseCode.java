package com.vk.flowable.common.enums;

import java.text.MessageFormat;

/**
 * 错误码
 */
public enum ResponseCode {

    // 错误码只可新增或删除，不可修改
    SUCCESS("0000", "success"),
    ERROR("0001", "error");

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
