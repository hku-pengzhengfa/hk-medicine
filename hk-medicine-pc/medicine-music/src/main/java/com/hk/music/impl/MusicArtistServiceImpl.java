package com.hk.music.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.music.api.entity.MusicArtist;
import com.hk.music.api.mapper.MusicArtistMapper;
import com.hk.music.api.service.MusicArtistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 艺术家业务
 *
 * @author pengzhengfa
 */
@Slf4j
@Service
public class MusicArtistServiceImpl extends ServiceImpl<MusicArtistMapper, MusicArtist>
        implements MusicArtistService {

    @Override
    public MusicArtist selectSingerInfo(Long singerId) {
        QueryWrapper<MusicArtist> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", singerId);
        return baseMapper.selectOne(queryWrapper);
    }
}
