package com.hk.music.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hk.common.core.base.Result;
import com.hk.music.api.dto.MusicCategoryDto;
import com.hk.music.api.entity.MusicCategory;
import com.hk.music.api.service.MusicCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author pengzhengfa
 */
@Api(tags = "风格查询")
@RestController
public class MusicCategoryController {

    @Autowired
    private MusicCategoryService musicCategoryService;

    @ApiOperation("查询音乐风格")
    @RequestMapping(value = "/selectMusicStyle", method = RequestMethod.POST)
    public Result<IPage<MusicCategory>> selectMusicStyle(@Valid @RequestBody MusicCategoryDto musicCategoryDto) {
        return Result.success(musicCategoryService.selectMusicStyle(musicCategoryDto), "Request succeeded");
    }
}
