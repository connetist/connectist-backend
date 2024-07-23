package org.example.apigatewayservice.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.example.apigatewayservice.dto.request.TokenRequest;
import org.example.apigatewayservice.dto.response.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.example.apigatewayservice.feign.TokenClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;


@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {



    private final SecretKey secretKey;
    TokenClient tokenClient;
    private final ObjectMapper objectMapper;

    private static final String ACCESS_TOKEN = "access-token";
    private static final String REFRESH_TOKEN = "refresh-token";

    public AuthorizationHeaderFilter(@Lazy TokenClient tokenClient, @Value("${token.secret}") String secret) {
        super(Config.class);
        this.tokenClient = tokenClient;
        this.secretKey =
                 new SecretKeySpec(
                         secret.getBytes(StandardCharsets.UTF_8),
                         SignatureAlgorithm.HS256.getJcaName()
                 );
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {
            log.info("Authorization header filter started");
            ServerHttpRequest request = exchange.getRequest();

            HttpCookie accessToken = request.getCookies().getFirst(ACCESS_TOKEN);

            if (accessToken == null){
                return onError(exchange, "No AccessToken", HttpStatus.UNAUTHORIZED);
            }

            String accessTokenValue = accessToken.getValue();

            if (!isJwtValid(accessTokenValue)){
                HttpCookie refreshToken = request.getCookies().getFirst(REFRESH_TOKEN);
                if (refreshToken == null){
                    return onError(exchange,"No refreshToken", HttpStatus.UNAUTHORIZED);
                }

                String refreshTokenValue = refreshToken.getValue();

                if (!isJwtValid(refreshTokenValue)){
                    return onError(exchange,"RefreshToken is not valid", HttpStatus.UNAUTHORIZED);
                }

                try{

                    String response = tokenClient.getTokens(new TokenRequest(accessTokenValue, refreshTokenValue));
                    log.info(response);

                    TokenResponse tokenResponse = objectMapper.readValue(response, TokenResponse.class);
                    // 기존 cookie 삭제
                    exchange.getResponse().addCookie(ResponseCookie.from(ACCESS_TOKEN, null).maxAge(0).build());
                    exchange.getResponse().addCookie(ResponseCookie.from(REFRESH_TOKEN, null).maxAge(0).build());

                    // 새로운 cookie 추가
                    exchange.getResponse().addCookie(ResponseCookie.from(ACCESS_TOKEN, tokenResponse.getData().getAccessToken())
                            .path("/")
                            .httpOnly(true)
                            .secure(true)
                            .build());
                    exchange.getResponse().addCookie(ResponseCookie.from(REFRESH_TOKEN, tokenResponse.getData().getRefreshToken())
                            .path("/")
                            .httpOnly(true)
                            .secure(true)
                            .build());
                }catch (Exception e){
                    log.error(e.getMessage());
                    return onError(exchange,"Failed to get new Tokens",HttpStatus.UNAUTHORIZED);
                }
                return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
            }
            return chain.filter(exchange);


        };
    }

    public static class Config {
        // Put configuration properties here
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        log.error(err);
        return response.setComplete();
    }

    private boolean isJwtValid(String jwt){

        boolean returnValue = true;
        String subject = null;

        try{
            JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
            subject = jwtParser.parseClaimsJws(jwt).getBody().get("userId", String.class);
            log.info("subject: {}", subject);
        } catch (Exception ex){
            log.error(ex.getMessage());
            returnValue = false;
        }

        if(subject== null || subject.isEmpty()){
            returnValue = false;
        }
        return returnValue;
    }


}
