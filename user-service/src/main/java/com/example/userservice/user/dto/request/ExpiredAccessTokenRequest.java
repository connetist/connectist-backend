package com.example.userservice.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExpiredAccessTokenRequest {
    private String accessToken;
    private String refreshToken;
}
