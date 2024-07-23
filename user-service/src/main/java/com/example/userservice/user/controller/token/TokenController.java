package com.example.userservice.user.controller.token;

import com.example.userservice.user.dto.response.token.TokenResponse;
import com.example.userservice.user.dto.request.token.ExpiredAccessTokenRequest;
import com.example.userservice.user.dto.request.RequestCheck;
import com.example.userservice.user.dto.response.GlobalResponse;
import com.example.userservice.user.service.token.JwtTokenService;
import com.example.userservice.util.exception.code.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.userservice.user.dto.response.GlobalResponse.of;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/token")
public class TokenController {

    private final JwtTokenService tokenService;

    @PostMapping
    public ResponseEntity<GlobalResponse<TokenResponse>> verifyToken(
            @RequestBody ExpiredAccessTokenRequest expiredAccessTokenRequest
    ) {
        log.info("token controller called");
        new RequestCheck(expiredAccessTokenRequest).check();

        TokenResponse tokenResponse = tokenService.verifyToken(expiredAccessTokenRequest.getAccessToken(), expiredAccessTokenRequest.getRefreshToken());

        return of(SuccessCode.OK, tokenResponse);
    }

}
