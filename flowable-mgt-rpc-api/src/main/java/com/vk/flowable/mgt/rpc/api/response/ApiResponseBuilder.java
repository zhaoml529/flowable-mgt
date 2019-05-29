package com.vk.flowable.mgt.rpc.api.response;

/**
 * Created by zml on 2019/5/29.
 */
public class ApiResponseBuilder {
    public static final String SuccessCode = "0000";
    public static final String SuccessMessage = "操作成功";

    public ApiResponseBuilder() {
    }

    public static ApiResponse<Void> buildSuccess() {
        return buildSuccess(null);
    }

    public static <T> ApiResponse<T> buildSuccess(T data) {
        return buildSuccess(SuccessMessage, data);
    }

    public static <T> ApiResponse<T> buildSuccess(String msg, T data) {
        return build(SuccessCode, msg, data);
    }

    public static <T> ApiResponse<T> buildError(String code, String msg) {
        return buildError(code, msg, null);
    }

    public static <T> ApiResponse<T> buildError(String code, String msg, T data) {
        return build(code, msg, data);
    }

    static <T> ApiResponse<T> build(String code, String msg, T data) {
        ApiResponse<T> response = new ApiResponse();
        response.setCode(code);
        response.setMessage(msg);
        response.setData(data);
        response.setSuccess(SuccessCode.equals(code));
        return response;
    }
}
