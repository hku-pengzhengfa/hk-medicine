package com.hk.music.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hk.common.core.base.Result;
import com.hk.music.api.dto.UserRaffleDetailDto;
import com.hk.music.api.entity.UserRaffleDetails;
import com.hk.music.api.service.UserRaffleDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author pengzhengfa
 */
@Api(tags = "抽奖业务")
@RestController
public class UserRaffleDetailsController {

    @Autowired
    private UserRaffleDetailsService userRaffleDetailsService;

    @ApiOperation("用户开奖手动发送奖励")
    @RequestMapping(value = "/sendReward", method = RequestMethod.GET)
    public Result<Boolean> sendReward(@RequestParam("id") Long id) {
        return Result.success(userRaffleDetailsService.sendReward(id), "Request succeeded");
    }

    @ApiOperation("查看用户7天内的中奖列表")
    @RequestMapping(value = "/userRafflePage", method = RequestMethod.POST)
    public Result<IPage<UserRaffleDetails>> userRafflePage(@Valid @RequestBody UserRaffleDetailDto raffleDetailDto) {
        return Result.success(userRaffleDetailsService.userRafflePage(raffleDetailDto), "Request succeeded");
    }

    @ApiOperation("查看7天内用户的中奖名单(真实中奖名单)或者自己的中奖记录")
    @RequestMapping(value = "/rewardPage", method = RequestMethod.POST)
    public Result<IPage<UserRaffleDetails>> rewardPage(@Valid @RequestBody UserRaffleDetailDto raffleDetailDto) {
        return Result.success(userRaffleDetailsService.rewardPage(raffleDetailDto), "Request succeeded");
    }

    @ApiOperation("查看7天内用户的中奖名单,真实用户+虚拟用户")
    @RequestMapping(value = "/rewardList", method = RequestMethod.GET)
    public Result<List<String>> rewardList(@RequestParam("num") Integer num) {
        return Result.success(userRaffleDetailsService.rewardList(num), "Request succeeded");
    }
}
