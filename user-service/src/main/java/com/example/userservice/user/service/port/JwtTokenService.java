package com.example.userservice.user.service.port;

import com.example.userservice.user.domain.User;
import com.example.userservice.user.domain.token.Token;
import com.example.userservice.user.domain.token.UserWithToken;

public interface JwtTokenService {

    String accessToken(User user);

    Token refreshToken(User user);

    UserWithToken verifyToken(String accessToken, String refreshToken);
}
