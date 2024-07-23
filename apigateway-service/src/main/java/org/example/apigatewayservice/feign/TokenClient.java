package org.example.apigatewayservice.feign;

import org.example.apigatewayservice.dto.request.TokenRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "tokenClient", url = "http://localhost:9014")
public interface TokenClient {

    @PostMapping("/user-service/api/token")
    String getTokens(@RequestBody TokenRequest tokenRequest);
}