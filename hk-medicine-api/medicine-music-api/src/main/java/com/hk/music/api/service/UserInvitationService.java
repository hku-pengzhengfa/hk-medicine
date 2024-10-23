package com.hk.music.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hk.music.api.dto.UserInvitationDto;
import com.hk.music.api.entity.UserInvitation;

/**
 * @author pengzhengfa
 */
public interface UserInvitationService {

    IPage<UserInvitation> selectUserInvitationPage(UserInvitationDto userInvitationDto);
}
