package com.hk.music.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.music.api.dto.MusicCategoryDto;
import com.hk.music.api.entity.MusicCategory;
import com.hk.music.api.mapper.MusicCategoryMapper;
import com.hk.music.api.service.MusicCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author pengzhengfa
 */
@Slf4j
@Service
public class MusicCategoryServiceImpl extends ServiceImpl<MusicCategoryMapper, MusicCategory>
        implements MusicCategoryService {

    @Override
    public IPage<MusicCategory> selectMusicStyle(MusicCategoryDto musicCategoryDto) {
        Page<MusicCategory> page = new Page<>(musicCategoryDto.getPageNo(), musicCategoryDto.getPageSize());
        Integer type = musicCategoryDto.getType();
        LambdaQueryWrapper<MusicCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!type.equals(0), MusicCategory::getType, type);
        return baseMapper.selectPage(page, wrapper);
    }
}
