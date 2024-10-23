package com.hk.music.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.music.api.dto.LikeDto;
import com.hk.music.api.entity.MusicInfo;
import com.hk.music.api.entity.MusicMyFavorite;
import com.hk.music.api.mapper.MusicMyFavoriteMapper;
import com.hk.music.api.service.MusicMyFavoriteService;
import com.hk.music.api.service.UserInfoService;
import com.hk.music.exception.HkException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pengzhengfa
 */
@Slf4j
@Service
public class MusicMyFavoriteServiceImpl extends ServiceImpl<MusicMyFavoriteMapper, MusicMyFavorite>

        implements MusicMyFavoriteService {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public IPage<MusicInfo> selectLikeMusicPage(LikeDto likeDto) {
        IPage<MusicInfo> page = new Page<>(likeDto.getPageNo(), likeDto.getPageSize());
        Long userId = userInfoService.selectUserId();
        return baseMapper.selectLikeMusicPage(page, userId);
    }

    @Override
    public IPage<MusicInfo> selectLikeMusicTaPage(LikeDto likeDto) {
        Long userId = likeDto.getUserId();
        if (userId == null) {
            throw new HkException(500, "userId cannot be empty");
        }
        IPage<MusicInfo> page = new Page<>(likeDto.getPageNo(), likeDto.getPageSize());
        return baseMapper.selectLikeMusicPage(page, userId);
    }
}
