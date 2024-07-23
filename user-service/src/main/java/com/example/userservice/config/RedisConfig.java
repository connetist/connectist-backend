package com.example.userservice.config;

import com.example.userservice.user.domain.token.Token;
import com.example.userservice.user.infrastructure.entity.TokenEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<String, TokenEntity> redisTemplate() {
        RedisTemplate<String, TokenEntity> redisTemplate = new RedisTemplate<>();

        Jackson2JsonRedisSerializer<TokenEntity> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(TokenEntity.class);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
}
