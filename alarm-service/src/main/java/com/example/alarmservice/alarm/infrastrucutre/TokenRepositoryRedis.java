package com.example.alarmservice.alarm.infrastrucutre;

import com.example.alarmservice.alarm.infrastrucutre.entity.TokenEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryRedis implements TokenRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private ValueOperations<String, String> valueOperations;

    @PostConstruct
    public void setOperations() {
        valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public String saveToken(String id, String token) {
        TokenEntity tokenEntity = new TokenEntity(id, token);
        valueOperations.set(id, tokenEntity.getToken());
        return tokenEntity.getToken();
    }

    @Override
    public String findTokenById(String id) {
        return valueOperations.get(id);
    }

    @Override
    public String deleteToken(String id) {
        return valueOperations.getAndDelete(id);
    }

    public String updateToken(String id, String token) {
        // TODO ; update하는 logic 필요
        return token;
    }

}
