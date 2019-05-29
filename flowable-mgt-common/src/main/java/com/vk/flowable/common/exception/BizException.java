package com.vk.flowable.common.exception;

import com.vk.flowable.common.enums.ResponseCode;

import java.io.Serializable;
import java.text.MessageFormat;

public class BizException extends RuntimeException implements Serializable {

    private String code = "0001";
    private String msg;
    private Throwable e;

    public BizException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BizException(ResponseCode responseCode) {
        super(responseCode.msg);
        this.code = responseCode.code;
        this.msg = responseCode.msg;
    }

    public BizException(ResponseCode responseCode, Object...args) {
        super("code:" + responseCode.code + ", msg:" + MessageFormat.format(responseCode.msg, args));

        this.code = responseCode.code;
        this.msg = responseCode.msg;
        if (args != null && args.length > 0) {
            this.msg = MessageFormat.format(responseCode.msg, args);
        }
    }

    public BizException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BizException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.e = e;
    }

    public BizException(String code, String msg, Throwable e) {
        super(msg, e);
        this.code = code;
        this.msg = msg;
        this.e = e;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public Throwable getE() {
        return this.e;
    }
}
