package com.hk.music.api.service;

/**
 * @author pengzhengfa
 */
public interface MusicHistoryService {

    /**
     * 添加音乐历史播放记录
     * @param musicId
     * @return
     */
    boolean addMusicHistory(Integer musicId);

    /**
     * 删除音乐历史播放记录
     * @param historyId
     * @return
     */
    boolean deleteMusicHistory(Integer historyId);
}
