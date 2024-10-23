package com.hk.music.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hk.music.api.dto.LikeDto;
import com.hk.music.api.entity.MusicInfo;

/**
 * @author pengzhengfa
 */
public interface MusicMyFavoriteService {

    IPage<MusicInfo> selectLikeMusicPage(LikeDto likeDto);

    IPage<MusicInfo> selectLikeMusicTaPage(LikeDto likeDto);
}
