package org.example.apigatewayservice.feign;

import jakarta.ws.rs.Path;
import org.apache.el.parser.Token;
import org.example.apigatewayservice.response.TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "tokenClient")
public interface TokenClient {

    @PostMapping("/user-service/api/token")
    TokenResponse getTokens(@PathVariable String refreshToken);
}