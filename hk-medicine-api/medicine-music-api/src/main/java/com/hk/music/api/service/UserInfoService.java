package com.hk.music.api.service;

import com.hk.music.api.entity.UserInfo;

import java.util.List;

/**
 * @author pengzhengfa
 */
public interface UserInfoService {

    /**
     * 更新用户头像或昵称
     *
     * @param userInfo
     * @return
     */
    boolean updateUserInfo(UserInfo userInfo);

    /**
     * 获取userId
     *
     * @return
     */
    Long selectUserId();

    /**
     * 查询用户昵称
     *
     * @param userIdList
     * @return
     */
    List<String> selectNickNameList(List<Long> userIdList);

    /**
     * 查询随机的用户,保证用户里面包含已经创建的音乐
     *
     * @param num
     * @return
     */
    List<UserInfo> selectRandomUserList(Integer num);

    /**
     * 查询用户信息
     *
     * @return
     */
    UserInfo selectUserInfo();
}
