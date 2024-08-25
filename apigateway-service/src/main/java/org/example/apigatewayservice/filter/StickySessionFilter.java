package org.example.apigatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@Slf4j
@Component
public class StickySessionFilter extends AbstractGatewayFilterFactory<StickySessionFilter.Config> {

    private final DiscoveryClient discoveryClient;
    public StickySessionFilter(DiscoveryClient discoveryClient) {
        super(Config.class);
        this.discoveryClient = discoveryClient;
    }


    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String portCookieValue = Optional.ofNullable(request.getCookies().getFirst("server-port"))
                    .map(cookie -> cookie.getValue())
                    .orElse(null);
            log.info(portCookieValue);

            if (portCookieValue != null) {
                String serviceId = "USER-SERVICE";  // 서비스 ID
                List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);

                // 포트에 맞는 인스턴스 선택
                Optional<ServiceInstance> instanceOpt = instances.stream()
                        .peek(instance -> System.out.println("Instance Port: " + instance.getPort()))  // 포트 번호 출력
                        .filter(instance -> String.valueOf(instance.getPort()).equals(portCookieValue))
                        .findFirst();


                if (instanceOpt.isPresent()) {
                    URI newUri = URI.create(instanceOpt.get().getUri().toString() + request.getURI().getRawPath());
                    ServerHttpRequest newRequest = request.mutate().uri(newUri).build();
                    ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
                    return chain.filter(newExchange);
                }else{
                    log.info("NOT EXIST");
                }
            }

            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Put the configuration properties here
    }
}
