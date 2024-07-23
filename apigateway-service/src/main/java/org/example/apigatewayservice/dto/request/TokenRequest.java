package org.example.apigatewayservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TokenRequest {
    private String accessToken;
    private String refreshToken;
}
