package com.hk.music.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hk.common.core.base.Result;
import com.hk.music.api.dto.ScoreLogDto;
import com.hk.music.api.entity.MusicUserScoreLog;
import com.hk.music.api.service.MusicUserScoreLogService;
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
@Api(tags = "用户分数")
@RestController
public class UserScoreLogServiceController {

    @Autowired
    private MusicUserScoreLogService musicUserScoreLogService;

    @ApiOperation(value = "获取积分明细")
    @RequestMapping(value = "/selectUserScorePage", method = RequestMethod.POST)
    public Result<IPage<MusicUserScoreLog>> selectUserScorePage(@Valid @RequestBody ScoreLogDto scoreLogDto) {
        return Result.success(musicUserScoreLogService.selectUserScorePage(scoreLogDto), "Request succeeded");
    }

}
