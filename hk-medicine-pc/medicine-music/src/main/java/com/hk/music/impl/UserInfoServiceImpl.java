package com.hk.music.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.common.core.util.JwtUtil;
import com.hk.common.core.util.ThreadPoolUtil;
import com.hk.music.api.entity.UserInfo;
import com.hk.music.api.mapper.UserInfoMapper;
import com.hk.music.api.service.MusicInfoService;
import com.hk.music.api.service.UserInfoService;
import com.hk.music.exception.HkException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pengzhengfa
 */
@Slf4j
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
        implements UserInfoService {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MusicInfoService musicInfoService;

    @Autowired
    private RedisTemplate<String, UserInfo> redisTemplate;

    @Value("${user.userInfoKey}")
    private String userInfoKey;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserInfo(UserInfo userInfo) {
        Long userId = 0L;
        UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
        try {
            if (userInfo != null) {
                updateWrapper.eq("id", userId);
                if (StringUtils.isNotBlank(userInfo.getAvatar())) {
                    updateWrapper.set("avatar", userInfo.getAvatar());
                }
                if (StringUtils.isNotBlank(userInfo.getNickname())) {
                    updateWrapper.set("nickname", userInfo.getNickname());
                }
                int update = baseMapper.update(null, updateWrapper);
                if (update > 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            log.info("更新用户信息失败:{}", e);
            throw new RuntimeException("更新用户信息失败:{}", e);
        }
        return false;
    }

    @Override
    public Long selectUserId() {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new HkException(500, "Not token");
        }
        boolean tokenExpired = JwtUtil.isTokenExpired(token);
        if (tokenExpired) {
            throw new HkException(500, "Token expiration");
        }
        Long userId = JwtUtil.getUserId(token);
        if (ObjectUtils.isEmpty(userId)) {
            throw new HkException(500, "User does not exist");
        }
        return userId;
    }

    @Override
    public List<String> selectNickNameList(List<Long> userIdList) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", userIdList);
        List<UserInfo> userInfoList = baseMapper.selectList(queryWrapper);
        if (!userInfoList.isEmpty()) {
            String salt = "**";
            return userInfoList.stream()
                    .map(userInfo -> {
                        String nickname = userInfo.getNickname();
                        String shortNickname = nickname.length() > 3 ? nickname.substring(0, 3) : nickname;
                        return shortNickname + salt;
                    })
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public List<UserInfo> selectRandomUserList(Integer num) {
        List<Long> userIdList = musicInfoService.selectMusicToUserId();
        if (!userIdList.isEmpty()) {
            QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("id", userIdList);
            return baseMapper.selectList(queryWrapper);
        }
        return Collections.emptyList();
    }

    @Override
    public UserInfo selectUserInfo() {
        Long userId = selectUserId();
        String redisKey = userInfoKey + userId;
        UserInfo redisUserInfo = redisTemplate.opsForValue().get(redisKey);
        if (redisUserInfo != null) {
            Long expireTime = redisTemplate.getExpire(redisKey);
            if (expireTime != null && expireTime > 0) {
                return redisUserInfo;
            }
        }
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        UserInfo userInfo = baseMapper.selectOne(queryWrapper);
        if (userInfo != null) {
            ThreadPoolUtil.execute(() -> {
                redisTemplate.opsForValue().set(redisKey, userInfo, Duration.ofDays(1));
            });
        }
        return userInfo;
    }
}
