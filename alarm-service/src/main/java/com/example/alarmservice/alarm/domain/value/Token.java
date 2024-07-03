package com.example.alarmservice.alarm.domain.value;

import com.example.alarmservice.alarm.dto.request.TokenRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Token {

    private final String tokenString;
    private final String id;

    @Builder
    public Token(String tokenString, String id) {
        this.tokenString = tokenString;
        this.id = id;
    }

    public static Token of(String token, String id) {
        return Token.builder()
                .tokenString(token).id(id).build();
    }

    public static Token from(TokenRequest tokenRequest) {
        return Token.builder()
                .tokenString(tokenRequest.getToken())
                .id(tokenRequest.getId())
                .build();
    }

}
