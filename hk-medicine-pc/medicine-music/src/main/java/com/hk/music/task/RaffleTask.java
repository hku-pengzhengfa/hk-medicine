package com.hk.music.task;

import com.hk.music.api.service.UserRaffleDetailsService;
import com.hk.music.api.service.UserRaffleService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 用户开奖和用户抽奖定时任务
 *
 * @author pengzhengfa
 */
@Slf4j
@Component
public class RaffleTask {

    @Autowired
    private UserRaffleService userRaffleService;

    @Autowired
    private UserRaffleDetailsService userRaffleDetailsService;

    @Value("${nft.raffleTotal}")
    private Integer raffleTotal;

    @Value("${nft.ethAmountTotal}")
    private double ethAmountTotal;

    /**
     * 用户抽奖
     */
    @XxlJob("userRaffle")
    public void userRaffle() {
        userRaffleService.userRaffle(raffleTotal);
    }

    /**
     * 用户开奖
     */
    @XxlJob("openUserRaffle")
    public void openUserRaffle() {
        userRaffleDetailsService.openUserRaffle(ethAmountTotal);
    }
}
