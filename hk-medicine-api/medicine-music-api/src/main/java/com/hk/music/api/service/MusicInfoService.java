package com.hk.music.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hk.music.api.dto.MusicDto;
import com.hk.music.api.entity.MusicInfo;
import java.util.List;

/**
 * @author pengzhengfa
 */
public interface MusicInfoService {

    /**
     * AI热歌推荐
     *
     * @param musicDto
     * @return
     */
    IPage<MusicInfo> selectAiHotRecommendPage(MusicDto musicDto);

    /**
     * AI歌曲排行榜
     *
     * @param musicDto
     * @return
     */
    IPage<MusicInfo> selectAiRankingListPage(MusicDto musicDto);

    /**
     * 查询音乐的用户id,后面通过用户id可以查到用户数据
     *
     * @return
     */
    List<Long> selectMusicToUserId();

    /**
     * 歌手歌曲分页列表
     *
     * @param musicDto
     * @return
     */
    IPage<MusicInfo> selectSingerMusicPage(MusicDto musicDto);

    /**
     * 查询我发布的音乐分页列表
     *
     * @param musicDto
     * @return
     */
    IPage<MusicInfo> selectPublishPage(MusicDto musicDto);

    /**
     * 查询我的历史播放列表
     * @param musicDto
     * @return
     */
    IPage<MusicInfo> selectMusicHistoryPage(MusicDto musicDto);
}
