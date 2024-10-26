package com.hk.music.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.common.core.util.TimeUtils;
import com.hk.music.api.entity.MusicHistory;
import com.hk.music.api.mapper.MusicHistoryMapper;
import com.hk.music.api.service.MusicHistoryService;
import com.hk.music.api.service.UserInfoService;
import com.hk.music.exception.HkException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.concurrent.TimeUnit;

/**
 * @author pengzhengfa
 */
@Slf4j
@Service
public class MusicHistoryServiceImpl extends ServiceImpl<MusicHistoryMapper, MusicHistory>
        implements MusicHistoryService {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addMusicHistory(Integer musicId) {
        Long userId = userInfoService.selectUserId();
        String lockKey = "musicHistoryLock:" + userId + ":" + musicId;
        RLock lock = redissonClient.getLock(lockKey);
        try {
            if (lock.tryLock(5, 2, TimeUnit.SECONDS)) {
                LambdaQueryWrapper<MusicHistory> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(MusicHistory::getUserId, userId)
                        .eq(MusicHistory::getMusicId, musicId)
                        .orderByDesc(MusicHistory::getId);
                MusicHistory history = getOne(queryWrapper, false);
                if (history != null) {
                    this.removeById(history.getId());
                }
                MusicHistory musicHistory = new MusicHistory();
                musicHistory.setUserId(userId);
                musicHistory.setMusicId(musicId);
                musicHistory.setCreateTime(TimeUtils.currentTime());
                musicHistory.setUpdateTime(TimeUtils.currentTime());
                try {
                    return save(musicHistory);
                } catch (Exception e) {
                    log.error("添加音乐历史播放记录失败:{}", e);
                    throw new RuntimeException("添加音乐历史播放记录失败", e);
                }
            } else {
                log.warn("获取锁失败,用户id:{},音乐id:{}", userId, musicId);
                return false;
            }
        } catch (InterruptedException e) {
            log.error("获取锁时发生异常: {}", e);
            Thread.currentThread().interrupt();
            return false;
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteMusicHistory(Integer historyId) {
        Long userId = userInfoService.selectUserId();
        MusicHistory musicHistory = baseMapper.selectById(historyId);
        if (musicHistory==null && userId==null){
            throw new HkException(500,"Music history does not exist");
        }
        try {
            int delete = baseMapper.deleteById(historyId);
            if (delete>0){
                return true;
            }
        }catch (Exception e){
            log.error("删除历史音乐播放记录失败:{}",e);
            throw new RuntimeException("删除历史音乐播放记录失败:{}",e);
        }
        return false;
    }
}