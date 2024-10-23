package com.hk.music.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.common.core.util.TimeUtils;
import com.hk.music.api.entity.MusicUserScore;
import com.hk.music.api.entity.MusicUserScoreLog;
import com.hk.music.api.mapper.MusicUserScoreMapper;
import com.hk.music.api.service.MusicUserScoreLogService;
import com.hk.music.api.service.MusicUserScoreService;
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
public class MusicUserScoreServiceImpl extends ServiceImpl<MusicUserScoreMapper, MusicUserScore>
        implements MusicUserScoreService {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private MusicUserScoreLogService musicUserScoreLogService;

    @Override
    public MusicUserScore selectMusicUserScoreInfo(Long userId) {
        LambdaQueryWrapper<MusicUserScore> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MusicUserScore::getUserId, userId);
        return getOne(queryWrapper);
    }

    /**
     * 添加积分
     *
     * @param userId
     * @param score
     * @param subType
     * @param relationId
     * @param memo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMusicUserScore(Long userId, Integer score, Integer subType, String relationId, String memo) {
        RLock lock = redissonClient.getLock("musicUserScoreLock:" + userId);
        boolean lockAcquired = false;
        try {
            lockAcquired = lock.tryLock(10, 5, TimeUnit.SECONDS);
            if (!lockAcquired) {
                throw new HkException(500, "Request too fast");
            }
            //添加积分
            String currentTime = TimeUtils.currentTime();
            MusicUserScore musicUserScore = selectMusicUserScoreInfo(userId);
            if (musicUserScore == null) {
                musicUserScore = new MusicUserScore();
                musicUserScore.setUserId(userId);
                musicUserScore.setScore(score);
                musicUserScore.setTotalScore(score);
                musicUserScore.setCreateTime(currentTime);
                musicUserScore.setUpdateTime(currentTime);
                baseMapper.insert(musicUserScore);
            } else {
                int beforeScore = musicUserScore.getScore();
                UpdateWrapper<MusicUserScore> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("user_id", userId)
                        .eq("score", beforeScore)
                        .setSql("score = score + " + score)
                        .setSql("total_score = total_score + " + score)
                        .set("update_time", currentTime);
                int update = baseMapper.update(null, updateWrapper);
                if (update != 1) {
                    log.error("Add score error. userId:{}, subType:{}, relationId:{}", userId, subType, relationId);
                    throw new HkException(500, "Add score error");
                }
                musicUserScore.setScore(beforeScore + score);
                musicUserScore.setTotalScore(musicUserScore.getTotalScore() + score);
                musicUserScore.setUpdateTime(currentTime);
                baseMapper.updateById(musicUserScore);

                //添加积分详情
                MusicUserScoreLog scoreLog = new MusicUserScoreLog();
                scoreLog.setUserId(userId);
                scoreLog.setScore(score);
                scoreLog.setBefore(beforeScore);
                scoreLog.setAfter(score);
                scoreLog.setRelationId(relationId);
                scoreLog.setFromType(subType / 1000);
                scoreLog.setSubType(subType);
                scoreLog.setCreateTime(currentTime);
                scoreLog.setUpdateTime(currentTime);
                scoreLog.setMemo(memo);
                musicUserScoreLogService.addMusicUserScoreLog(scoreLog);
                //更新用户等级
            }
        } catch (Exception e) {
            log.error("添加积分失败: {}", e.getMessage(), e);
            throw new RuntimeException("添加积分失败", e);
        } finally {
            if (lockAcquired && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
