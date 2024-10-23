package com.hk.music.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.common.core.util.ThreadPoolUtil;
import com.hk.common.core.util.TimeUtils;
import com.hk.music.api.entity.UserRaffle;
import com.hk.music.api.entity.UserRaffleDetails;
import com.hk.music.api.mapper.UserRaffleMapper;
import com.hk.music.api.service.UserMedalService;
import com.hk.music.api.service.UserRaffleDetailsService;
import com.hk.music.api.service.UserRaffleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户抽奖(必中用户配置等业务)
 *
 * @author pengzhengfa
 */
@Slf4j
@Service
public class UserRaffleServiceImpl extends ServiceImpl<UserRaffleMapper, UserRaffle>
        implements UserRaffleService {

    @Autowired
    private UserMedalService userMedalService;

    @Autowired
    private UserRaffleDetailsService userRaffleDetailsService;

    @Override
    public void userRaffle(Integer raffleTotal) {
        // 1. 查询必中奖用户
        QueryWrapper<UserRaffle> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        List<UserRaffle> mustUserList = baseMapper.selectList(queryWrapper);
        int mustUserCount = mustUserList.size();
        // 2. 查询非必中用户的user_id
        List<Long> noMustUserIdList = userMedalService.selectUserMedalList(raffleTotal);
        // 3.存储最终的中奖用户id,确保唯一性
        Set<Long> winnerUserIds = new HashSet<>(raffleTotal);

        // 4. 判断抽奖逻辑
        if (mustUserCount == 0) {
            // 4.1 如果没有必中奖用户,从非必中用户中随机抽取 raffleTotal 个中奖用户
            winnerUserIds.addAll(selectRandomUserIds(noMustUserIdList, raffleTotal));
        } else {
            if (raffleTotal == mustUserCount) {
                // 4.2 如果必中奖用户数等于抽奖用户数,所有必中奖用户获奖
                for (UserRaffle mustUser : mustUserList) {
                    winnerUserIds.add(mustUser.getUserId());
                }
            } else if (raffleTotal > mustUserCount) {
                // 4.3 如果必中奖用户数小于抽奖用户数,先全部必中奖,再从非必中用户中补充
                for (UserRaffle mustUser : mustUserList) {
                    winnerUserIds.add(mustUser.getUserId());
                }
                // 只从非必中用户中选取 raffleTotal - mustUserCount 个用户
                winnerUserIds.addAll(selectRandomUserIds(noMustUserIdList, raffleTotal - mustUserCount));
            } else {
                // 4.4 如果必中奖用户数大于抽奖用户数,从必中奖用户中随机选择 raffleTotal 个用户
                List<Long> mustUserIdList = mustUserList.stream().map(UserRaffle::getUserId).collect(Collectors.toList());
                winnerUserIds.addAll(selectRandomUserIds(mustUserIdList, raffleTotal));
            }
        }

        // 5. 构建抽奖详情列表,并异步保存到数据库中
        List<UserRaffleDetails> userRaffleDetailsList = new ArrayList<>();
        String currentTime = TimeUtils.currentTime();
        for (Long userId : winnerUserIds) {
            UserRaffleDetails raffleDetail = createRaffleDetail(userId, currentTime);
            userRaffleDetailsList.add(raffleDetail);
        }

        // 6. 使用线程池异步执行保存操作
        ThreadPoolUtil.execute(() -> {
            userRaffleDetailsService.addUserRaffleDetails(userRaffleDetailsList);
        });
    }

    /**
     * 创建抽奖详情
     *
     * @param userId      用户id
     * @param currentTime 当前时间
     * @return UserRaffleDetails 抽奖详情
     */
    private UserRaffleDetails createRaffleDetail(Long userId, String currentTime) {
        UserRaffleDetails raffleDetails = new UserRaffleDetails();
        raffleDetails.setUserId(userId);
        raffleDetails.setStatus(0); // 设置未开奖状态
        raffleDetails.setPayStatus(0); // 设置未支付状态
        raffleDetails.setCreateTime(currentTime);
        raffleDetails.setUpdateTime(currentTime);
        return raffleDetails;
    }

    /**
     * 从用户id列表中随机选取指定数量的用户id
     *
     * @param userIdList 用户ID列表
     * @param total      需要选取的用户数量
     * @return 选取的用户ID列表
     */
    private List<Long> selectRandomUserIds(List<Long> userIdList, Integer total) {
        // 先打乱顺序,然后选取前total个
        Collections.shuffle(userIdList);
        return userIdList.stream().limit(total).collect(Collectors.toList());
    }
}