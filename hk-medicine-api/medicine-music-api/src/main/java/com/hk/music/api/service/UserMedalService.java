package com.hk.music.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hk.music.api.dto.UserMedalDto;
import com.hk.music.api.entity.UserMedal;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author pengzhengfa
 */
public interface UserMedalService {

    /**
     * 查询用户勋章集合(查询最近7天内登录的用户,并且勋章大于1,如果不足则时间往前推)
     *
     * @param total
     * @return
     */
    List<Long> selectUserMedalList(Integer total);

    /**
     * 查询用户勋章分页列表
     * @param userMedalDto
     * @return
     */
    IPage<UserMedal> selectUserMedalPage(UserMedalDto userMedalDto);

    /**
     * 勋章佩戴
     * @param medalId
     * @return
     */
    boolean wearUserMedal(@PathVariable Integer medalId);

    /**
     * 取消其他勋章佩戴
     * @param userId
     * @param medalId
     * @return
     */
    boolean cancelWearOther(Long userId, Integer medalId);
}
