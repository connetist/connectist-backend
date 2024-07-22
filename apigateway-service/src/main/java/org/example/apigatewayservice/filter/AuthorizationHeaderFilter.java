package org.example.apigatewayservice.filter;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.example.apigatewayservice.util.Token;
import org.springframework.beans.factory.annotation.Value;
import org.apache.el.parser.Token;
import org.example.apigatewayservice.feign.TokenClient;
import org.example.apigatewayservice.response.TokenResponse;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;


@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {


//     private final Environment env;
//     private final SecretKey secretKey;
//     private final WebClient.Builder webClientBuilder;
//     private final ResponseBodyResultHandler responseBodyResultHandler;

//     public AuthorizationHeaderFilter(Environment env, @Value("${token.secret}") String secret, WebClient.Builder webClientBuilder, ResponseBodyResultHandler responseBodyResultHandler) {
//         super(Config.class);
//         this.env = env;
//         this.secretKey =
//                 new SecretKeySpec(
//                         secret.getBytes(StandardCharsets.UTF_8),
//                         SignatureAlgorithm.HS256.getJcaName()
//                 );
//         this.webClientBuilder = webClientBuilder;
//         this.responseBodyResultHandler = responseBodyResultHandler;
    Environment env;
    TokenClient tokenClient;

    public AuthorizationHeaderFilter(Environment env, @Lazy TokenClient tokenClient) {
        super(Config.class);
        this.env = env;
        this.tokenClient = tokenClient;
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {
            log.info("Authorization header filter started");
            ServerHttpRequest request = exchange.getRequest();



            HttpCookie accessToken = request.getCookies().getFirst("access-token");

            if (accessToken == null){
                return onError(exchange, "No AccessToken", HttpStatus.UNAUTHORIZED);
            }

            String accessTokenValue = accessToken.getValue();

            if (!isJwtValid(accessTokenValue)){
                HttpCookie refreshToken = request.getCookies().getFirst("refresh-token");
                if (refreshToken == null){
                    return onError(exchange,"No refreshToken", HttpStatus.UNAUTHORIZED);
                }

                String refreshTokenValue = refreshToken.getValue();

                if (!isJwtValid(refreshTokenValue)){
                    return onError(exchange,"RefreshToken is not valid", HttpStatus.UNAUTHORIZED);
                }

                try{
                    TokenResponse response = tokenClient.getTokens(refreshTokenValue);
                    exchange.getResponse().addCookie(ResponseCookie.from("access-token",response.getAccessToken())
                            .path("/")
                            .httpOnly(true)
                            .secure(true)
                            .build());
                    exchange.getResponse().addCookie(ResponseCookie.from("refresh-token",response.getRefreshToken())
                            .path("/")
                            .httpOnly(true)
                            .secure(true)
                            .build());
                }catch (Exception e){
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

    private Mono<String> validRefreshToken(String accessToken, String refreshToken) {
        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);

        WebClient webClient = webClientBuilder
                .baseUrl("http://localhost:9014")
                .build();
        return webClient.post()
                .uri("/user-service/api/users/token")
                .bodyValue(token)
                .retrieve().bodyToMono(String.class);
    }
}
