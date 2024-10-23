package com.hk.music.api.service;

import com.hk.music.api.entity.MusicUserScore;

/**
 * @author pengzhengfa
 */
public interface MusicUserScoreService {

    /**
     * 查询用户积分信息
     *
     * @param userId
     * @return
     */
    MusicUserScore selectMusicUserScoreInfo(Long userId);

    /**
     * 增加积分
     *
     * @param userId
     * @param score
     * @param subType
     * @param relationId
     * @param memo
     */
    void addMusicUserScore(Long userId, Integer score, Integer subType, String relationId, String memo);
}
