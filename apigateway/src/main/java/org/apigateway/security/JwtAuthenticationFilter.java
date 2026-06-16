package org.apigateway.security;

import org.apigateway.enums.Role;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        System.out.println("Incoming Path: " + path);

        // ✅ ONLY PUBLIC APIs
        if (path.startsWith("/auth")) {
            return chain.filter(exchange);
        }

        // ✅ ALL OTHER APIs MUST HAVE JWT

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }

        String token = authHeader.substring(7);

        try {
            jwtUtil.validateToken(token);

            Role role = jwtUtil.extractRole(token);
            Long userId = jwtUtil.extractUserId(token);

            System.out.println("✅ ROLE = " + role + " USERID = " + userId);

            return forwardWithHeaders(exchange, chain, role, userId);

        } catch (Exception e) {
            System.out.println("❌ JWT ERROR: " + e.getMessage());
            return unauthorized(exchange);
        }
    }

    // ✅ Forward request with headers (VERY IMPORTANT)
    private Mono<Void> forwardWithHeaders(
            ServerWebExchange exchange,
            GatewayFilterChain chain,
            Role role,
            Long userId) {

        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                .header("X-User-Id", String.valueOf(userId))
                .header("X-User-Role", role.name())
                .build();

        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    // ✅ Utility methods
    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private Mono<Void> forbidden(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}