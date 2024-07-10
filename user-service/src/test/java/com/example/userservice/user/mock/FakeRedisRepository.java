package com.example.userservice.user.mock;

import com.example.userservice.user.domain.token.Token;
import com.example.userservice.user.infrastructure.entity.TokenEntity;
import com.example.userservice.user.infrastructure.token.TokenRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FakeRedisRepository implements TokenRepository {
    private final Map<String, TokenEntity> tokenMap = new ConcurrentHashMap<>();

    @Override
    public Token findById(String id) {
        Token token = tokenMap.get(id).toModel();
        return token;
    }

    @Override
    public Token save(TokenEntity entity) {
        TokenEntity tokenEntity = tokenMap.put(entity.getId(), entity);
        assert tokenEntity != null;
        return tokenEntity.toModel();
    }

    @Override
    public void delete(String id) {
        tokenMap.remove(id);
    }
}
