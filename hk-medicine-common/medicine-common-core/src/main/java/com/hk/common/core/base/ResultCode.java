package com.hk.common.core.base;

import lombok.Getter;

/**
 * @author pengzhengfa
 */
@Getter
public enum ResultCode {

    success(200, "Request succeeded"),
    failed(500, "Request failed");

    private Integer code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
