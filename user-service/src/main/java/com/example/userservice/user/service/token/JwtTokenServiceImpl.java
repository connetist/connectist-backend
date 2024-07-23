package com.example.userservice.user.service.token;

import com.example.userservice.user.dto.response.token.TokenResponse;
import com.example.userservice.user.dto.response.token.UserWithToken;
import com.example.userservice.user.infrastructure.user.UserRepository;
import com.example.userservice.util.token.JwtUtil;
import com.example.userservice.util.exception.ErrorCode;
import com.example.userservice.user.domain.user.User;
import com.example.userservice.user.domain.token.Token;
import com.example.userservice.util.exception.code.GlobalException;
import com.example.userservice.user.infrastructure.entity.TokenEntity;
import com.example.userservice.user.infrastructure.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
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
                    .refreshToken(jwtUtil.createRefreshTokenJWT(user.getId(), user.getStatus().toString(), EXPIRED_DAY))
                    .expiration(EXPIRED_DAY)
                    .build();

            return tokenRepository.save(TokenEntity.from(token));
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public TokenResponse verifyToken(String accessToken, String refreshToken) {
        String userId = null;
        String userRole = null;
        try {
            userId = jwtUtil.getUserId(refreshToken);
            userRole = jwtUtil.getUserRole(refreshToken);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new GlobalException(ErrorCode.LOGIN_ERROR);
        }
        Token findToken = tokenRepository.findById(userId);
        if (findToken.getRefreshToken().equals(refreshToken)) {
            String newAccessToken = jwtUtil.createAccessTokenJWT(userId, userRole);
            String newRefreshToken = jwtUtil.createRefreshTokenJWT(userId, userRole,EXPIRED_DAY);

            Token token = Token.builder()
                    .id(userId)
                    .refreshToken(newRefreshToken)
                    .expiration(EXPIRED_DAY)
                    .build();

            tokenRepository.delete(userId);
            tokenRepository.save(TokenEntity.from(token));

            return new TokenResponse(newAccessToken, newRefreshToken);

        }else{
            throw new GlobalException(ErrorCode.LOGIN_ERROR);
        }

    }
}
