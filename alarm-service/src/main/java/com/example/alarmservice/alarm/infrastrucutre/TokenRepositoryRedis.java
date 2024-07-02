package com.example.alarmservice.alarm.infrastrucutre;

import com.example.alarmservice.alarm.infrastrucutre.entity.TokenEntity;
import com.example.alarmservice.util.exception.ErrorCode;
import com.example.alarmservice.util.exception.GlobalException;
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
        try {
            valueOperations.set(id, tokenEntity.getToken());
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return tokenEntity.getToken();
    }

    @Override
    public String findTokenById(String id) {
        try {
            return valueOperations.get(id);
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String deleteToken(String id) {
        try {
            return valueOperations.getAndDelete(id);
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public String updateToken(String id, String token) {
        try {
            valueOperations.set(id, token);
            return token;
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}
