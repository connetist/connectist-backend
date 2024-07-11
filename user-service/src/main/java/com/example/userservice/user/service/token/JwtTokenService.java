package com.example.userservice.user.service.token;

import com.example.userservice.user.domain.user.User;
import com.example.userservice.user.domain.token.Token;
import com.example.userservice.user.dto.response.token.UserWithToken;

public interface JwtTokenService {

    String accessToken(User user);

    Token refreshToken(User user);

    UserWithToken verifyToken(String accessToken, String refreshToken);
}
