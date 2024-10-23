package com.hk.common.core.base;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author pengzhengfa
 */
@Data
@ToString
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;

    private String msg;

    private T data;

    private boolean success;

    public Result() {
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> success(T data, String msg) {
        return new Result<>(ResultCode.success.getCode(), msg, data);
    }

    public static <T> Result<T> successMsg(String msg) {
        return new Result<>(ResultCode.success.getCode(), msg, null);
    }

    public static <T> Result<T> failed(String msg) {
        return new Result<>(ResultCode.failed.getCode(), msg, null);
    }

    public static <T> Result<T> failed(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }

    public static <T> Result<T> failed(String msg, T data) {
        return new Result<>(ResultCode.failed.getCode(), msg, data);
    }


    public static <T> Result<T> failed(ResultCode errorCode) {
        return new Result<>(errorCode.getCode(), errorCode.getMsg(), null);
    }

    public static <T> Result<T> failed(ResultCode errorCode, T data) {
        return new Result<>(errorCode.getCode(), errorCode.getMsg(), data);
    }

    public boolean isSuccess() {
        return this.code == ResultCode.success.getCode();
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
