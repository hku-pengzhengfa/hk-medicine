package com.hk.music.config;

import com.hk.music.api.entity.UserInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

/**
 * @author pengzhengfa
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        /**
         * 创建模板
         */
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        /**
         * 设置连接工厂
         */
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        /**
         * 设置序列化工具
         */
        GenericJackson2JsonRedisSerializer jsonRedisSerializer =
                new GenericJackson2JsonRedisSerializer();
        /**
         * key和 hashKey采用 string序列化
         */
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        /**
         * value和 hashValue采用json序列化
         */
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, UserInfo> userInfoRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, UserInfo> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    @Bean
    public RedisTemplate<String, List<String>> listNameRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, List<String>> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}