package com.example.userservice.util.token;

import com.example.userservice.util.clock.ClockHolder;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private SecretKey secretKey;

    public JwtUtil(ClockHolder clockHolder, @Value("${spring.jwt_secret_key}") String secret){
        this.secretKey =
                new SecretKeySpec(
                        secret.getBytes(StandardCharsets.UTF_8),
                        Jwts.SIG.HS256.key().build().getAlgorithm()
                );
    }

    public String getUserId(String token){
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload()
                .get("userId", String.class);
    };

    public String getUserRole(String token){
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload()
                .get("userRole", String.class);
    };

    public boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload()
                .getExpiration().before(new Date());
    }

    public String createAccessTokenJWT(String userId, String role) {

        Date now = new Date();
        Date exp = new Date(System.currentTimeMillis() + 10 * 1000);


        return Jwts.builder()
                .claim("userId", userId)
                .claim("role", role)
                .issuedAt(now)
                .expiration(exp)
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshTokenJWT(String userId, int expireDay) {
        Date now = new Date();
        Date exp = new Date(System.currentTimeMillis() + 1 * (1000 * 60 * 60 * 24 * expireDay));

        return Jwts.builder()
                .claim("userId", userId)
                .issuedAt(now)
                .expiration(exp)
                .signWith(secretKey)
                .compact();
    }

}
