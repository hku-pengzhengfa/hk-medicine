package com.hk.music.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.music.api.dto.ScoreLogDto;
import com.hk.music.api.entity.MusicUserScoreLog;
import com.hk.music.api.mapper.MusicUserScoreLogMapper;
import com.hk.music.api.service.UserInfoService;
import com.hk.music.api.service.MusicUserScoreLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pengzhengfa
 */
@Slf4j
@Service
public class MusicUserScoreLogServiceImpl extends ServiceImpl<MusicUserScoreLogMapper, MusicUserScoreLog>
        implements MusicUserScoreLogService {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public IPage<MusicUserScoreLog> selectUserScorePage(ScoreLogDto scoreLogDto) {
        Long userId = userInfoService.selectUserId();
        Integer pageNo = scoreLogDto.getPageNo();
        Integer pageSize = scoreLogDto.getPageSize();
        Page<MusicUserScoreLog> page = new Page<>(pageNo, pageSize);
        QueryWrapper<MusicUserScoreLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByDesc("id");
        IPage<MusicUserScoreLog> userScoreLogIPage = baseMapper.selectPage(page, queryWrapper);
        List<MusicUserScoreLog> records = userScoreLogIPage.getRecords();
        List<MusicUserScoreLog> distinctRecords = records.stream()
                .distinct()
                .collect(Collectors.toList());
        IPage<MusicUserScoreLog> distinctPage = new Page<>(pageNo, pageSize);
        distinctPage.setRecords(distinctRecords);
        distinctPage.setTotal(distinctRecords.size());
        return distinctPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMusicUserScoreLog(MusicUserScoreLog musicUserScoreLog) {
        try {
            save(musicUserScoreLog);
        } catch (Exception e) {
            log.error("添加积分明细失败:{}", e);
            throw new RuntimeException("添加积分明细失败:{}", e);
        }
    }
}
