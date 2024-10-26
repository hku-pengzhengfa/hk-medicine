package com.hk.music.controller;
import com.hk.common.core.base.Result;
import com.hk.music.api.service.MusicHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengzhengfa
 */
@Api(tags = "历史播放的音乐")
@RestController
public class MusicHistoryController {

    @Autowired
    private MusicHistoryService musicHistoryService;

    @ApiOperation("添加歌曲到历史播放记录")
    @RequestMapping(value = "/addMusicHistory", method = RequestMethod.GET)
    public Result<Boolean> addMusicHistory(Integer musicId) {
        return Result.success(musicHistoryService.addMusicHistory(musicId), "Request succeeded");
    }

    @ApiOperation("删除历史播放记录")
    @RequestMapping(value = "/deleteMusicHistory", method = RequestMethod.GET)
    public Result<Boolean> deleteMusicHistory(Integer historyId) {
        return Result.success(musicHistoryService.deleteMusicHistory(historyId), "Request succeeded");
    }
}
