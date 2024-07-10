package com.example.userservice.user.controller.token;

import com.example.userservice.user.dto.response.token.UserWithToken;
import com.example.userservice.user.dto.request.token.ExpiredAccessTokenRequest;
import com.example.userservice.user.dto.request.RequestCheck;
import com.example.userservice.user.dto.response.GlobalResponse;
import com.example.userservice.user.service.token.JwtTokenService;
import com.example.userservice.util.exception.code.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.userservice.user.dto.response.GlobalResponse.of;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/token")
public class TokenController {

    private final JwtTokenService tokenService;

    @GetMapping
    public ResponseEntity<GlobalResponse<UserWithToken>> verifyToken(
            @RequestBody ExpiredAccessTokenRequest expiredAccessTokenRequest
    ) {
        new RequestCheck(expiredAccessTokenRequest).check();

        UserWithToken userWithToken = tokenService.verifyToken(expiredAccessTokenRequest.getAccessToken(), expiredAccessTokenRequest.getRefreshToken());

        return of(SuccessCode.OK, userWithToken);
    }

}
