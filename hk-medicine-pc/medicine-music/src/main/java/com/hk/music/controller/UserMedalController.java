package com.hk.music.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hk.common.core.base.Result;
import com.hk.music.api.dto.UserMedalDto;
import com.hk.music.api.entity.UserMedal;
import com.hk.music.api.service.UserMedalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * @author pengzhengfa
 */
@Api(tags = "用户勋章接口")
@RestController
public class UserMedalController {

    @Autowired
    private UserMedalService userMedalService;

    @ApiOperation(value = "获取用户勋章列表")
    @RequestMapping(value = "/selectUserMedalPage", method = RequestMethod.POST)
    public Result<IPage<UserMedal>> selectUserMedalPage(@Valid @RequestBody UserMedalDto userMedalDto) {
        return Result.success(userMedalService.selectUserMedalPage(userMedalDto), "Request succeeded");
    }

    @ApiOperation(value = "佩戴勋章")
    @RequestMapping(value = "/wearUserMedal", method = RequestMethod.GET)
    public Result<Boolean> wearUserMedal(@PathVariable Integer medalId) {
        return Result.success(userMedalService.wearUserMedal(medalId), "Request succeeded");
    }
}
