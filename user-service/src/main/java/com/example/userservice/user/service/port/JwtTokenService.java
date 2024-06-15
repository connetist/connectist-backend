package com.example.userservice.user.service.port;

import com.example.userservice.user.domain.User;
import com.example.userservice.user.domain.token.Token;

public interface JwtTokenService {

    String accessToken(User user);

    Token refreshToken(User user);
}
