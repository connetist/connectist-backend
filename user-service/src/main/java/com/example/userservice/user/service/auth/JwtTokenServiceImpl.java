package com.example.userservice.user.service.auth;

import com.example.userservice.user.domain.User;
import com.example.userservice.user.domain.token.Token;
import com.example.userservice.user.infrastructure.TokenEntity;
import com.example.userservice.user.service.port.JwtTokenService;
import com.example.userservice.user.service.port.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;

    @Override
    public String accessToken(User user) {
        return jwtUtil.createAccessTokenJWT(user.getId(), String.valueOf(user.getStatus()));
    }

    // refresh token 발급하기
    @Override
    public Token refreshToken(User user) {
        // 먼저 삭제
        tokenRepository.delete(user.getId());

        int expiredDay = 14;
        Token token = Token.builder()
                .id(user.getId())
                .refreshToken(jwtUtil.createRefreshTokenJWT(user.getId(), expiredDay))
                .expiration(expiredDay)
                .build();
        // 저장 후 return
        return tokenRepository.save(TokenEntity.from(token));
    }



}
