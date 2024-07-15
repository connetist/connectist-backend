package org.example.apigatewayservice.filter;

import jdk.jfr.Category;
import lombok.extern.slf4j.Slf4j;
import org.example.apigatewayservice.response.TokenResponse;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import io.jsonwebtoken.Jwts;


@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    Environment env;

    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

//            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
//                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
//            }
//
//            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
//            String jwt = authorizationHeader.replace("Bearer", "");

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
//                    TokenResponse response = generateNewAccessToken(refreshToken);
//                    exchange.getResponse().addCookie(ResponseCookie.from("access-token",response.getAccessToken())
//                            .path("/")
//                            .httpOnly(true)
//                            .secure(true)
//                            .build());
//                    exchange.getResponse().addCookie(ResponseCookie.from("refresh-token",response.getRefreshToken())
//                            .path("/")
//                            .httpOnly(true)
//                            .secure(true)
//                            .build());
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
            subject = Jwts.parser().setSigningKey(env.getProperty("token.secret"))
                    .parseClaimsJws(jwt).getBody()
                    .getSubject();
        } catch (Exception ex){
            returnValue = false;
        }

        if(subject== null || subject.isEmpty()){
            returnValue = false;
        }
//        return false;
        return returnValue;
    }
}
