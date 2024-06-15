package com.example.userservice.user.infrastructure;

import com.example.userservice.user.domain.token.Token;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenEntity {

    private String id;
    private String refreshToken;
    private Integer expiration;


    public static TokenEntity from(Token token) {
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setId(token.getId());
        tokenEntity.setRefreshToken(token.getRefreshToken());
        tokenEntity.setExpiration(token.getExpiration());
        return tokenEntity;
    }
}
