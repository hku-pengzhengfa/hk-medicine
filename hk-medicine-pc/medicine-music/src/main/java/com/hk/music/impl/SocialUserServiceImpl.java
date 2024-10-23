package com.hk.music.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.common.core.en.UserSourceEnum;
import com.hk.music.api.entity.SocialUser;
import com.hk.music.api.mapper.SocialUserMapper;
import com.hk.music.api.service.SocialUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 第三方社交平台用户
 *
 * @author pengzhengfa
 */
@Slf4j
@Service
public class SocialUserServiceImpl extends ServiceImpl<SocialUserMapper, SocialUser>
        implements SocialUserService {

    @Override
    public String selectWalletAddress(Long userId) {
        LambdaQueryWrapper<SocialUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SocialUser::getUserId, userId)
                .eq(SocialUser::getSource, UserSourceEnum.METAMASK.getKey());
        SocialUser socialUser = getOne(queryWrapper, false);
        return socialUser == null ? "" : socialUser.getUuid();
    }
}
