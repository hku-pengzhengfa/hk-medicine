package com.hk.music.config;

import com.hk.music.handler.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author pengzhengfa
 */
@Configuration
public class HkuConfig implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**",
                        "/selectAiRankingListPage/**", "/selectAiHotRecommendPage/**",
                        "/rewardList/**", "/selectMusicStyle/**", "/selectLikeMusicTaPage/**");
    }
}