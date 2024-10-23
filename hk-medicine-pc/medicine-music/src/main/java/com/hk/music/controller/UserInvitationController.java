package com.hk.music.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hk.common.core.base.Result;
import com.hk.music.api.dto.UserInvitationDto;
import com.hk.music.api.entity.UserInvitation;
import com.hk.music.api.service.UserInvitationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author pengzhengfa
 */
@Api(tags = "用户邀请")
@RestController
public class UserInvitationController {

    @Autowired
    private UserInvitationService userInvitationService;

    @ApiOperation(value = "用户邀请分页查询")
    @RequestMapping(value = "/selectUserInvitationPage", method = RequestMethod.POST)
    public Result<IPage<UserInvitation>> selectUserInvitationPage(@Valid @RequestBody UserInvitationDto userInvitationDto) {
        return Result.success(userInvitationService.selectUserInvitationPage(userInvitationDto), "Request succeeded");
    }
}
