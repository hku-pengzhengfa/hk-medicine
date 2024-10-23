package com.hk.music.api.service;

/***
 * @author pengzhengfa
 */
public interface UserRaffleService {

    /**
     * 用户抽奖用于发eth币
     *
     * @param raffleTotal
     */
    void userRaffle(Integer raffleTotal);
}
