package com.example.userservice.user.infrastructure.token;

import com.example.userservice.user.domain.token.Token;
import com.example.userservice.user.infrastructure.entity.TokenEntity;

public interface TokenRepository {

    Token findById(String id);

    Token save(TokenEntity entity);

    void delete(String id);
}
