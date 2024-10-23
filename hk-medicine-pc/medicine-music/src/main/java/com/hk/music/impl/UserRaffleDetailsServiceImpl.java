package com.hk.music.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.common.core.util.NickNameUtil;
import com.hk.common.core.util.ThreadPoolUtil;
import com.hk.common.core.util.TimeUtils;
import com.hk.music.api.dto.UserRaffleDetailDto;
import com.hk.music.api.entity.UserRaffleDetails;
import com.hk.music.api.mapper.UserRaffleDetailsMapper;
import com.hk.music.api.service.EthTransactionService;
import com.hk.music.api.service.UserInfoService;
import com.hk.music.api.service.UserRaffleDetailsService;
import com.hk.music.api.service.SocialUserService;
import com.hk.music.exception.HkException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户抽奖详情
 *
 * @author pengzhengfa
 */
@Slf4j
@Service
public class UserRaffleDetailsServiceImpl extends ServiceImpl<UserRaffleDetailsMapper, UserRaffleDetails>
        implements UserRaffleDetailsService {

    @Autowired
    private SocialUserService socialUserService;

    @Autowired
    private EthTransactionService ethTransactionService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RedisTemplate<String, List<String>> redisTemplate;

    @Value("${user.nicknameKey}")
    private String nicknameKey;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 添加用户抽奖详情数据
     *
     * @param userRaffleDetailsList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUserRaffleDetails(List<UserRaffleDetails> userRaffleDetailsList) {
        try {
            saveBatch(userRaffleDetailsList);
        } catch (Exception e) {
            log.error("插入抽奖详情失败");
            throw new RuntimeException("插入抽奖详情失败:{}", e);
        }
    }

    /**
     * 用户开奖
     *
     * @param ethAmountTotal
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void openUserRaffle(BigDecimal ethAmountTotal) {
        QueryWrapper<UserRaffleDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 0);
        List<UserRaffleDetails> userRaffleDetailsList = baseMapper.selectList(queryWrapper);
        BigDecimal ethAmount = BigDecimal.ZERO;
        if (userRaffleDetailsList.size() > 0) {
            ethAmount = ethAmountTotal.divide(BigDecimal.valueOf(userRaffleDetailsList.size()));
        }
        for (UserRaffleDetails info : userRaffleDetailsList) {
            info.setStatus(1);
            info.setPayStatus(0);
            info.setEthAmount(ethAmount);
            info.setUpdateTime(TimeUtils.currentTime());
        }
        if (!userRaffleDetailsList.isEmpty()) {
            try {
                updateBatchById(userRaffleDetailsList);
            } catch (Exception e) {
                log.error("用户开奖异常:{}", e);
                throw new RuntimeException("用户开奖异常:{}", e);
            }
        }
    }

    @Override
    public boolean sendReward(Long id) {
        Long userId = userInfoService.selectUserId();
        RLock lock = redissonClient.getLock("rewardLock:" + id + userId);
        try {
            if (lock.tryLock(0, 6, TimeUnit.SECONDS)) {
                QueryWrapper<UserRaffleDetails> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id", id)
                        .eq("user_id", userId)
                        .ge("create_time", LocalDateTime.now().minusDays(7));
                UserRaffleDetails userRaffleDetails = baseMapper.selectOne(queryWrapper);
                // 1. 未中奖
                if (userRaffleDetails == null) {
                    throw new HkException(500, "Didn't win the prize");
                }
                // 2. 未开奖
                if (userRaffleDetails.getStatus() == 0) {
                    throw new HkException(500, "Not drawn yet");
                }
                // 3. 已经开奖过
                if (userRaffleDetails.getPayStatus() == 1) {
                    throw new HkException(500, "The lottery has already been won");
                }
                // 4. 获取用户的钱包地址
                String walletAddress = socialUserService.selectWalletAddress(userId);
                if (StringUtils.isBlank(walletAddress)) {
                    throw new HkException(500, "Your wallet address is invalid");
                }
                BigDecimal ethAmount = userRaffleDetails.getEthAmount();
                ethTransactionService.transferEth(id, walletAddress, ethAmount);
                return true;
            } else {
                throw new HkException(500, "Unable to acquire lock");
            }
        } catch (InterruptedException e) {
            log.error("Lock acquisition interrupted:{}", e);
            throw new HkException(500, "Lock acquisition interrupted");
        } finally {
            //5.主动释放锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    @Override
    public IPage<UserRaffleDetails> userRafflePage(UserRaffleDetailDto raffleDetailDto) {
        Long userId = userInfoService.selectUserId();
        IPage<UserRaffleDetails> page = new Page<>(raffleDetailDto.getPageNo(), raffleDetailDto.getPageSize());
        QueryWrapper<UserRaffleDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                .eq("status", 1)
                .ge("create_time", LocalDateTime.now().minusDays(7))
                .orderByDesc("create_time");
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public IPage<UserRaffleDetails> rewardPage(UserRaffleDetailDto raffleDetailDto) {
        Long userId = userInfoService.selectUserId();
        IPage<UserRaffleDetails> page = new Page<>(raffleDetailDto.getPageNo(), raffleDetailDto.getPageSize());
        QueryWrapper<UserRaffleDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("create_time", LocalDateTime.now().minusDays(7));
        if (userId != null) {
            queryWrapper.eq("user_id", userId);
        }
        queryWrapper.orderByDesc("create_time");
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<String> rewardList(Integer num) {
        List<String> nickNameList = redisTemplate.opsForValue().get(nicknameKey);
        if (nickNameList == null || nickNameList.isEmpty() || redisTemplate.getExpire(nicknameKey) <= 0) {
            QueryWrapper<UserRaffleDetails> queryWrapper = new QueryWrapper<>();
            queryWrapper.ge("create_time", LocalDateTime.now().minusDays(7));
            List<UserRaffleDetails> detailsList = baseMapper.selectList(queryWrapper);
            nickNameList = new ArrayList<>();
            if (!detailsList.isEmpty()) {
                List<Long> userIdList = detailsList.stream()
                        .map(UserRaffleDetails::getUserId)
                        .collect(Collectors.toList());
                if (!userIdList.isEmpty()) {
                    nickNameList.addAll(userInfoService.selectNickNameList(userIdList));
                }
            }
            int remainNum = num - nickNameList.size();
            if (remainNum > 0) {
                List<String> randomNicknameList = NickNameUtil.randomNicknameList(remainNum);
                if (randomNicknameList != null) {
                    nickNameList.addAll(randomNicknameList);
                }
            }
            nickNameList = nickNameList.stream().limit(num).collect(Collectors.toList());
            List<String> redisNickNameList = nickNameList;
            ThreadPoolUtil.execute(() -> {
                redisTemplate.delete(nicknameKey);
                redisTemplate.opsForValue().set(nicknameKey, redisNickNameList, Duration.ofDays(7));
            });
        }
        return nickNameList.stream().limit(num).collect(Collectors.toList());
    }
}
