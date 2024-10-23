package com.hk.music.controller;

import com.hk.common.core.base.Result;
import com.hk.music.api.entity.MusicArtist;
import com.hk.music.api.service.MusicArtistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author pengzhengfa
 */
@Api(tags = "音乐业务")
@RestController
public class MusicArtistController {

    @Autowired
    private MusicArtistService musicArtistService;

    @ApiOperation("歌手信息")
    @RequestMapping(value = "/selectSingerInfo", method = RequestMethod.POST)
    public Result<MusicArtist> selectSingerInfo(@RequestParam Long aid) {
        return Result.success(musicArtistService.selectSingerInfo(aid), "Request succeeded");
    }
}
