package com.hk.music.handler;

import com.hk.common.core.util.JwtUtil;
import com.hk.music.exception.HkException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author pengzhengfa
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new HkException(407, "Not login");
        }
        if (JwtUtil.isTokenExpired(token)) {
            throw new HkException(407, "Token has expired");
        }
        Long userId = JwtUtil.getUserId(token);
        if (ObjectUtils.isEmpty(userId) || !JwtUtil.verify(token, userId)) {
            throw new HkException(407, "Token verification failed");
        }
        JwtUtil.refreshToken(token);
        return true;
    }
}