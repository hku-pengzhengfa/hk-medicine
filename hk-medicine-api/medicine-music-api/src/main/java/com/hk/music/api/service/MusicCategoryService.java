package com.hk.music.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hk.music.api.dto.MusicCategoryDto;
import com.hk.music.api.entity.MusicCategory;

/**
 * @author pengzhengfa
 */
public interface MusicCategoryService {

    IPage<MusicCategory> selectMusicStyle(MusicCategoryDto musicCategoryDto);
}
