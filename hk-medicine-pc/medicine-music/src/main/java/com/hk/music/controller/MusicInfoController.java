package com.hk.music.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hk.common.core.base.Result;
import com.hk.music.api.dto.MusicDto;
import com.hk.music.api.entity.MusicInfo;
import com.hk.music.api.service.MusicInfoService;
import com.hk.music.api.vo.MusicInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author pengzhengfa
 */
@Api(tags = "音乐业务")
@RestController
public class MusicInfoController {

    @Autowired
    private MusicInfoService musicInfoService;


    @ApiOperation("AI热歌推荐")
    @RequestMapping(value = "/selectAiHotRecommendPage", method = RequestMethod.POST)
    public Result<IPage<MusicInfo>> selectAiHotRecommendPage(@Valid @RequestBody MusicDto musicDto) {
        return Result.success(musicInfoService.selectAiHotRecommendPage(musicDto), "Request succeeded");
    }

    @ApiOperation("AI歌曲排行榜")
    @RequestMapping(value = "/selectAiRankingListPage", method = RequestMethod.POST)
    public Result<IPage<MusicInfo>> selectAiRankingListPage(@Valid @RequestBody MusicDto musicDto) {
        return Result.success(musicInfoService.selectAiRankingListPage(musicDto), "Request succeeded");
    }

    @ApiOperation("歌手歌曲列表")
    @RequestMapping(value = "/selectSingerMusicPage", method = RequestMethod.POST)
    public Result<IPage<MusicInfoVo>> selectSingerMusicPage(@Valid @RequestBody MusicDto musicDto) {
        return Result.success(musicInfoService.selectSingerMusicPage(musicDto), "Request succeeded");
    }

    @ApiOperation("查询我发布的音乐列表")
    @RequestMapping(value = "/selectPublishPage", method = RequestMethod.POST)
    public Result<IPage<MusicInfo>> selectPublishPage(@Valid @RequestBody MusicDto musicDto) {
        return Result.success(musicInfoService.selectPublishPage(musicDto), "Request succeeded");
    }
}
