package com.hk.music.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hk.music.api.dto.UserRaffleDetailDto;
import com.hk.music.api.entity.UserRaffleDetails;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author pengzhengfa
 */
public interface UserRaffleDetailsService {

    /**
     * 添加开奖信息
     *
     * @param userRaffleDetailsList
     */
    void addUserRaffleDetails(List<UserRaffleDetails> userRaffleDetailsList);

    /**
     * 用户开奖
     *
     * @param ethAmountTotal
     */
    void openUserRaffle(BigDecimal ethAmountTotal);


    /**
     * 给中奖用户发eth币
     */
    boolean sendReward(Long id);


    /**
     * 用户中级记录
     *
     * @param raffleDetailDto
     * @return
     */
    IPage<UserRaffleDetails> userRafflePage(UserRaffleDetailDto raffleDetailDto);

    /**
     * 查看7天内的所有用户的中奖名单
     *
     * @param raffleDetailDto
     * @return
     */
    IPage<UserRaffleDetails> rewardPage(UserRaffleDetailDto raffleDetailDto);

    /**
     * 查看7天内真实+虚拟用户中奖名单
     *
     * @return
     */
    List<String> rewardList(Integer num);
}
