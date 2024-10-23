package com.hk.music.api.service;

import java.util.Set;

/**
 * @author pengzhengfa
 */
public interface UserLoginLogService {

    /**
     * 最近登录用户查询
     *
     * @return
     */
    Set<Long> selectUserIdToLogin();
}
