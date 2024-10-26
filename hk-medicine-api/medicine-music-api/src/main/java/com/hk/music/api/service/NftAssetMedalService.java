package com.hk.music.api.service;

/**
 * @author pengzhengfa
 */
public interface NftAssetMedalService {

    boolean selectNftAssetMedal(String walletAddress,Long userId);

    void insertNftAssetMedal(String walletAddress,Long userId);
}
