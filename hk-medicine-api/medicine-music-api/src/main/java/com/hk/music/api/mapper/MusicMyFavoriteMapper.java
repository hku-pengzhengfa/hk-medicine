package com.hk.music.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hk.music.api.entity.MusicInfo;
import com.hk.music.api.entity.MusicMyFavorite;

/**
 * @author pengzhengfa
 */
public interface MusicMyFavoriteMapper extends BaseMapper<MusicMyFavorite> {

    IPage<MusicInfo> selectLikeMusicPage(IPage<MusicInfo> page,Long userId);
}
