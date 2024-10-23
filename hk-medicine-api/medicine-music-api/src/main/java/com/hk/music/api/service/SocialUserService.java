package com.hk.music.api.service;

/**
 * @author pengzhengfa
 */
public interface SocialUserService {

    /**
     * 获取用户的钱包地址
     *
     * @param userId
     * @return
     */
    String selectWalletAddress(Long userId);
}
