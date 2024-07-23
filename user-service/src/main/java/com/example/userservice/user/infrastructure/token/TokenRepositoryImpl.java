package com.example.userservice.user.infrastructure.token;

import com.example.userservice.util.exception.ErrorCode;
import com.example.userservice.user.domain.token.Token;
import com.example.userservice.util.exception.code.GlobalException;
import com.example.userservice.user.infrastructure.entity.TokenEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    private final RedisTemplate<String, TokenEntity> redisTemplate;

    @Override

    public Token findById(String id) {
        try {
            ValueOperations<String, TokenEntity> valueOperations = redisTemplate.opsForValue();
            TokenEntity tokenEntity = valueOperations.get(id);
            assert tokenEntity != null;
            return tokenEntity.toModel();


        } catch (Exception e) {
            log.error(e.getMessage());
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }


    }

    @Override
    public Token save(TokenEntity entity) {
        try {
            ValueOperations<String, TokenEntity> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(entity.getId(), entity);
            redisTemplate.expire(entity.getId(), entity.getExpiration(), TimeUnit.DAYS);
            return entity.toModel();

        } catch (Exception e) {
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public void delete(String id) {
        try {
            if (isContain(id)) {
                redisTemplate.delete(id);
            }
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    private boolean isContain(String id) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(id));
    }
}
