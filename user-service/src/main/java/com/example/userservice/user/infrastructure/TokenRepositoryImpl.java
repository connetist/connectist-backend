package com.example.userservice.user.infrastructure;

import com.example.userservice.user.domain.token.Token;
import com.example.userservice.user.service.port.TokenRepository;
import com.example.userservice.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    private final RedisTemplate<String, TokenEntity> redisTemplate;

    @Override

    public Token findById(String id) {
        ValueOperations<String, TokenEntity> valueOperations = redisTemplate.opsForValue();
        try {
            TokenEntity entity = (TokenEntity) valueOperations.get(id);
            return Token.builder()
                    .id(entity.getId())
                    .refreshToken(entity.getRefreshToken())
                    .build();
        } catch (Exception e) {
            throw new NotFoundException(id);
        }
    }

    @Override
    public Token save(TokenEntity entity) {
        ValueOperations<String, TokenEntity> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(entity.getId(), entity);
        redisTemplate.expire(entity.getId(), entity.getExpiration(), TimeUnit.DAYS);
        if (!isContain(entity.getId())) {
            throw new NotFoundException(entity.getId());
        }
        return Token.builder()
                .id(entity.getId())
                .refreshToken(entity.getRefreshToken())
                .build();
    }

    @Override
    public void delete(String id) {
        if (isContain(id)) {
            redisTemplate.delete(id);
        }
    }

    private boolean isContain(String id) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(id));
    }
}
