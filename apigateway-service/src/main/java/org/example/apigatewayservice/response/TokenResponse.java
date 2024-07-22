package org.example.apigatewayservice.response;

import lombok.Getter;

@Getter
public class TokenResponse {
    String accessToken;
    String refreshToken;
}
