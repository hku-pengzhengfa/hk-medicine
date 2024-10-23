package com.hk.music.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.music.api.dto.UserInvitationDto;
import com.hk.music.api.entity.UserInvitation;
import com.hk.music.api.mapper.UserInvitationMapper;
import com.hk.music.api.service.UserInfoService;
import com.hk.music.api.service.UserInvitationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pengzhengfa
 */
@Slf4j
@Service
public class UserInvitationServiceImpl extends ServiceImpl<UserInvitationMapper, UserInvitation>
        implements UserInvitationService {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public IPage<UserInvitation> selectUserInvitationPage(UserInvitationDto userInvitationDto) {
        Long userId = userInfoService.selectUserId();
        Page<UserInvitation> page = new Page<>(userInvitationDto.getPageNo(),userInvitationDto.getPageSize());
        return baseMapper.selectUserInvitationPage(page,userId);
    }
}
