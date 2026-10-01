package com.ecommerce.api_gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final WebClient.Builder webClientBuilder;

    public AuthenticationFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            String path = exchange.getRequest().getURI().getPath();

            if (path.contains("/api/user/login") || path.contains("/api/user/register")) {
                return chain.filter(exchange);
            }

            // 1. Check if the Authorization header is present and valid
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return onError(exchange, "Missing or invalid authorization header", HttpStatus.UNAUTHORIZED);
            }

            // Extract the actual token string
            String token = authHeader.substring(7);

            // 2. Call the Auth Service's /validate endpoint via WebClient to verify the token
            return webClientBuilder.build()
                    .get()
                    .uri("http://localhost:9000/api/user/validate?token=" + token)
                    .retrieve()
                    .toBodilessEntity()
                    .flatMap(response -> {
                        // If valid, let the request proceed to the destination service
                        return chain.filter(exchange);
                    })
                    .onErrorResume(error -> {
                        // If the token is invalid or expired, reject the request
                        return onError(exchange, "Unauthorized access: Invalid token", HttpStatus.UNAUTHORIZED);
                    });
        };
    }

    private Mono<Void> onError(org.springframework.web.server.ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    public static class Config {
        // Configuration properties if needed
    }
}