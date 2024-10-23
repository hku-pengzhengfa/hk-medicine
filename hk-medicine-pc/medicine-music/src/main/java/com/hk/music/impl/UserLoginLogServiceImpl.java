package com.hk.music.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.music.api.entity.UserLoginLog;
import com.hk.music.api.mapper.UserLoginLogMapper;
import com.hk.music.api.service.UserLoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author pengzhengfa
 */
@Slf4j
@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLog>
        implements UserLoginLogService {

    @Override
    public Set<Long> selectUserIdToLogin() {
        QueryWrapper<UserLoginLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("login_time");
        List<UserLoginLog> userLoginLogList = baseMapper.selectList(queryWrapper);
        return userLoginLogList.stream()
                .map(UserLoginLog::getUserId)
                .collect(Collectors.toSet());
    }
}
