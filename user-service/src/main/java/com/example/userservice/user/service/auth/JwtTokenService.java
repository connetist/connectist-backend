package com.example.userservice.user.service.auth;

import com.example.userservice.user.domain.user.UserStatus;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
public class JwtTokenService {


    private Key key = Jwts.SIG.HS256.key().build();

    /**
     * login한 사용자에게 token 발급
     * @param userId : String, status : UserStatus
     * @return String
     */
    public String createToken(String userId, UserStatus status) {
        Date now = new Date();
        Date exp = new Date(System.currentTimeMillis() + 1 * (1000 * 60 * 60));
        return Jwts.builder()
                .header().add("type", "jwt").and()
                .claim("userId", userId)
                .claim("status", status)
                .issuedAt(now)
                .expiration(exp)
                .signWith(key)
                .compact();
    }

    /**
     * Request로부터 jwt를 가져옵니다.
     * @return String
     */
    public String getJwt() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("Authorization");
    }

}
