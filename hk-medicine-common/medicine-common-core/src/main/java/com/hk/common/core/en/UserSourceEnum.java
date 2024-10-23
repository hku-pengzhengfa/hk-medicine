package com.hk.common.core.en;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户来源的枚举
 * 用户状态:1=未激活,2=正常,3=已锁定,4=已注销
 *
 */
@AllArgsConstructor
@Getter
public enum UserSourceEnum {

    EMAIL("email", "邮箱"),
    MOBILE("mobile", "手机号"),
    METAMASK("metaMask", "metaMask钱包"),
    GOOGLE("google", "谷歌"),
    TELEGRAM("telegram", "纸飞机"),
    TWITTER("twitter", "推特x"),
    OKX("okx", "okx");

    private final String key;

    private final String desc;

    /**
     * 通过key查找对应的UserSourceEnum
     *
     * @param key 用户来源的key
     * @return 对应的UserSourceEnum枚举对象
     * @throws IllegalArgumentException 如果没有找到匹配的key则抛出异常
     */
    public static UserSourceEnum ofKey(String key) {
        for (UserSourceEnum enumObj : UserSourceEnum.values()) {
            if (enumObj.key.equalsIgnoreCase(key)) {
                return enumObj;
            }
        }
        throw new IllegalArgumentException("No matching UserSourceEnum for key: " + key);
    }
}
