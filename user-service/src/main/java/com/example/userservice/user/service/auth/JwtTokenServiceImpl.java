package com.example.userservice.user.service.auth;

import com.example.userservice.util.exception.ErrorCode;
import com.example.userservice.user.domain.User;
import com.example.userservice.user.domain.token.Token;
import com.example.userservice.util.exception.code.GlobalException;
import com.example.userservice.user.infrastructure.entity.TokenEntity;
import com.example.userservice.user.service.port.JwtTokenService;
import com.example.userservice.user.service.port.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;
    private static final int EXPIRED_DAY = 14;

    @Override
    public String accessToken(User user) {
        try {
            return jwtUtil.createAccessTokenJWT(user.getId(), String.valueOf(user.getStatus()));
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    // refresh token 발급하기
    @Override
    public Token refreshToken(User user) {
        tokenRepository.delete(user.getId());

        try {
            Token token = Token.builder()
                    .id(user.getId())
                    .refreshToken(jwtUtil.createRefreshTokenJWT(user.getId(), EXPIRED_DAY))
                    .expiration(EXPIRED_DAY)
                    .build();

            return tokenRepository.save(TokenEntity.from(token));
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }



}
