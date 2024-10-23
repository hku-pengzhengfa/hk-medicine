package com.hk.music.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hk.common.core.base.Result;
import com.hk.music.api.dto.LikeDto;
import com.hk.music.api.entity.MusicInfo;
import com.hk.music.api.service.MusicMyFavoriteService;
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
@Api(tags = "喜好的音乐")
@RestController
public class MusicMyFavoriteController {

    @Autowired
    private MusicMyFavoriteService myFavoriteService;

    @ApiOperation(value = "查询我的喜好音乐列表")
    @RequestMapping(value = "/selectLikeMusicPage", method = RequestMethod.POST)
    public Result<IPage<MusicInfo>> selectLikeMusicPage(@Valid @RequestBody LikeDto likeDto) {
        return Result.success(myFavoriteService.selectLikeMusicPage(likeDto), "Request succeeded");
    }

    @ApiOperation(value = "查询Ta喜好音乐列表")
    @RequestMapping(value = "/selectLikeMusicTaPage", method = RequestMethod.POST)
    public Result<IPage<MusicInfo>> selectLikeMusicTaPage(@Valid @RequestBody LikeDto likeDto) {
        return Result.success(myFavoriteService.selectLikeMusicTaPage(likeDto), "Request succeeded");
    }
}
