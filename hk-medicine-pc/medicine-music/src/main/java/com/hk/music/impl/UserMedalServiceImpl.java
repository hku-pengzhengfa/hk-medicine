package com.hk.music.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.common.core.util.TimeUtils;
import com.hk.music.api.dto.UserMedalDto;
import com.hk.music.api.entity.UserMedal;
import com.hk.music.api.mapper.UserMedalMapper;
import com.hk.music.api.service.UserInfoService;
import com.hk.music.api.service.UserLoginLogService;
import com.hk.music.api.service.UserMedalService;
import com.hk.music.exception.HkException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 用户勋章
 *
 * @author pengzhengfa
 */
@Slf4j
@Service
public class UserMedalServiceImpl extends ServiceImpl<UserMedalMapper, UserMedal>
        implements UserMedalService {

    @Autowired
    private UserLoginLogService userLoginLogService;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 查询用户勋章集合
     *
     * @param total
     * @return
     */
    @Override
    public List<Long> selectUserMedalList(Integer total) {
        List<Long> userMedalList = baseMapper.selectUserMedalList(total, userLoginLogService.selectUserIdToLogin());
        if (userMedalList.isEmpty()) {
            return Collections.emptyList();
        }
        Collections.shuffle(userMedalList);
        return userMedalList;
    }

    /**
     * 查询用户勋章分页列表
     * @param userMedalDto
     * @return
     */
    @Override
    public IPage<UserMedal> selectUserMedalPage(UserMedalDto userMedalDto) {
        Long userId = userInfoService.selectUserId();
        Page<UserMedal> page = new Page<>(userMedalDto.getPageNo(),userMedalDto.getPageSize());
        return baseMapper.selectUserMedalPage(page,userId);
    }

    @Override
    public boolean wearUserMedal(Integer medalId) {
        Long userId = userInfoService.selectUserId();
        QueryWrapper<UserMedal> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                .eq("medal_id", medalId)
                .and(wrapper -> wrapper.eq("expiration_time", 0).or().gt("expiration_time", System.currentTimeMillis() / 1000));
        UserMedal userMedal = baseMapper.selectOne(queryWrapper);
        if (userMedal==null){
            throw new HkException(500,"The medal does not exist");
        }
        String currentTime = TimeUtils.currentTime();
        userMedal.setWear(1);
        userMedal.setUpdateTime(currentTime);
        userMedal.setCreateTime(currentTime);
        boolean updateById = updateById(userMedal);
        boolean cancelWearOther = cancelWearOther(userId, medalId);
        if (updateById && cancelWearOther){
            return true;
        }
        return false;
    }

    @Override
    public boolean cancelWearOther(Long userId, Integer medalId) {
        LambdaUpdateWrapper<UserMedal> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(UserMedal::getWear, 0)
                .eq(UserMedal::getUserId, userId)
                .set(UserMedal::getUpdateTime, new Date())
                .ne(UserMedal::getMedalId, medalId);
        return update(updateWrapper);
    }
}
