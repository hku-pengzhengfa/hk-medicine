package com.hk.music.exception;

/**
 * @author pengzhengfa
 */
public class HkException extends RuntimeException {
    private Integer code;
    private String msg;

    public HkException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
