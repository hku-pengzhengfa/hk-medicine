package com.hk.music.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.music.api.dto.MusicDto;
import com.hk.music.api.entity.MusicInfo;
import com.hk.music.api.mapper.MusicInfoMapper;
import com.hk.music.api.service.MusicInfoService;
import com.hk.music.api.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 音乐
 *
 * @author pengzhengfa
 */
@Slf4j
@Service
public class MusicInfoServiceImpl extends ServiceImpl<MusicInfoMapper, MusicInfo> implements MusicInfoService {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public IPage<MusicInfo> selectAiHotRecommendPage(MusicDto musicDto) {
        Integer musicType = musicDto.getMusicType();
        QueryWrapper<MusicInfo> queryWrapper = new QueryWrapper<>();
        IPage<MusicInfo> page = new Page<>(musicDto.getPageNo(), musicDto.getPageSize());
        if (musicType == 1) {
            queryWrapper.orderByDesc("play_count");
        } else if (musicType == 2) {
            queryWrapper.orderByDesc("like_count");
        }
        queryWrapper.orderByDesc("create_time");
        IPage<MusicInfo> musicInfoIPage = baseMapper.selectPage(page, queryWrapper);
        musicInfoIPage.setTotal(musicDto.getPageSize());
        return musicInfoIPage;
    }

    @Override
    public IPage<MusicInfo> selectAiRankingListPage(MusicDto musicDto) {
        IPage<MusicInfo> page = new Page<>(musicDto.getPageNo(), musicDto.getPageSize());
        QueryWrapper<MusicInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("create_type", 1);
        queryWrapper.orderByDesc("like_count");
        IPage<MusicInfo> musicInfoIPage = baseMapper.selectPage(page, queryWrapper);
        musicInfoIPage.setTotal(musicDto.getPageSize());
        return musicInfoIPage;
    }

    @Override
    public List<Long> selectMusicToUserId() {
        QueryWrapper<MusicInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("order by rand() limit 6");
        List<MusicInfo> musicInfoList = baseMapper.selectList(queryWrapper);
        if (musicInfoList.isEmpty()) {
            return musicInfoList.stream()
                    .map(MusicInfo::getUserId)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public IPage<MusicInfo> selectSingerMusicPage(MusicDto musicDto) {
        Long userId = userInfoService.selectUserId();
        Page<MusicInfo> page = new Page<>(musicDto.getPageNo(), musicDto.getPageSize());
        return baseMapper.selectSingerMusicPage(page, musicDto.getSingerId(), userId);
    }

    @Override
    public IPage<MusicInfo> selectPublishPage(MusicDto musicDto) {
        Long userId = userInfoService.selectUserId();
        IPage<MusicInfo> page = new Page<>(musicDto.getPageNo(), musicDto.getPageSize());
        QueryWrapper<MusicInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByDesc("id");
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public IPage<MusicInfo> selectMusicHistoryPage(MusicDto musicDto) {
        Long userId = userInfoService.selectUserId();
        Page<MusicInfo> page = new Page<>(musicDto.getPageNo(), musicDto.getPageSize());
        return baseMapper.selectMusicHistoryPage(page,userId);
    }
}
