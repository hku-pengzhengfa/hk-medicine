package com.hk.music.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * eth执行状态
 * @author pengzhengfa
 */
@AllArgsConstructor
@Getter
public enum EthTransStatusEnum {
    /**
     * 用户状态: 0=初始、1=成功、2=失败、3=执行中
     */
    SUCCESS(1, "成功"),
    FAIL(2, "失败"),
    PROGRESS (3, "执行中"),;

    private final Integer code;

    private final String desc;


    public static EthTransStatusEnum getByCode(Integer code){
        for (EthTransStatusEnum enumObj : EthTransStatusEnum.values()) {
            if(enumObj.getCode().equals(code)){
                return enumObj;
            }
        }
        return null;
    }
}
