package com.hk.music.controller;

import com.hk.common.core.base.Result;
import com.hk.music.api.entity.UserInfo;
import com.hk.music.api.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author pengzhengfa
 */
@Api(tags = "用户业务")
@RestController
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation("更新用户头像或昵称")
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public Result<Boolean> updateUserInfo(@Valid @RequestBody UserInfo userInfo) {
        return Result.success(userInfoService.updateUserInfo(userInfo), "Request succeeded");
    }

    @ApiOperation("查询随机的用户")
    @RequestMapping(value = "/selectRandomUserList", method = RequestMethod.GET)
    public Result<List<UserInfo>> selectRandomUserList(@RequestParam("num") Integer num) {
        return Result.success(userInfoService.selectRandomUserList(num), "Request succeeded");
    }

    @ApiOperation("查询用户信息")
    @RequestMapping(value = "/selectUserInfo", method = RequestMethod.GET)
    public Result<UserInfo> selectUserInfo() {
        return Result.success(userInfoService.selectUserInfo(), "Request succeeded");
    }
}
