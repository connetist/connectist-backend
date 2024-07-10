package com.example.userservice.user.service.token;

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
import org.springframework.stereotype.Service;

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
                    .refreshToken(jwtUtil.createRefreshTokenJWT(user.getId(), EXPIRED_DAY))
                    .expiration(EXPIRED_DAY)
                    .build();

            return tokenRepository.save(TokenEntity.from(token));
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public UserWithToken verifyToken(String accessToken, String refreshToken) {
        String userId = null;
        String userRole = null;
        try {
            userId = jwtUtil.getUserId(accessToken);
            userRole = jwtUtil.getUserRole(accessToken);
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.LOGIN_ERROR);
        }
        Token findToken = tokenRepository.findById(userId);
        if (findToken.getRefreshToken().equals(refreshToken)) {
            String newAccessToken = jwtUtil.createAccessTokenJWT(userId, userRole);
            String newRefreshToken = jwtUtil.createRefreshTokenJWT(userId, EXPIRED_DAY);

            Token token = Token.builder()
                    .id(userId)
                    .refreshToken(newRefreshToken)
                    .expiration(EXPIRED_DAY)
                    .build();

            tokenRepository.delete(userId);
            tokenRepository.save(TokenEntity.from(token));

            return UserWithToken.builder()
                    .access(newAccessToken)
                    .refresh(newRefreshToken).build();

        }else{
            throw new GlobalException(ErrorCode.LOGIN_ERROR);
        }

    }
}
