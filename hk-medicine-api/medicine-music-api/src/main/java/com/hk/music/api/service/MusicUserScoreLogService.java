package com.hk.music.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hk.music.api.dto.ScoreLogDto;
import com.hk.music.api.entity.MusicUserScoreLog;

/**
 * @author pengzhengfa
 */
public interface MusicUserScoreLogService {

    /**
     * 积分明细分页列表
     *
     * @param scoreLogDto
     * @return
     */
    IPage<MusicUserScoreLog> selectUserScorePage(ScoreLogDto scoreLogDto);

    /**
     * 添加积分明细
     *
     * @param musicUserScoreLog
     */
    void addMusicUserScoreLog(MusicUserScoreLog musicUserScoreLog);
}
