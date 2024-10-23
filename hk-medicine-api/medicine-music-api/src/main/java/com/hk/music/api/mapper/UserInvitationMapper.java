package com.hk.music.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hk.music.api.entity.UserInvitation;
import org.apache.ibatis.annotations.Param;

/**
 * @author pengzhengfa
 */
public interface UserInvitationMapper extends BaseMapper<UserInvitation> {

    IPage<UserInvitation> selectUserInvitationPage(@Param("page") IPage<UserInvitation> page,
                                                   @Param("userId") Long userId);
}
