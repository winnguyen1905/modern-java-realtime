package com.winnguyen1905.talk.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.common.constant.AccountRole;

import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class SocketIOJwtFilter implements WebFilter {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Autowired
  private ReactiveJwtDecoder jwtDecoder;

  private static final String SOCKET_IO_PATH = "/socket.io/";

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    String path = exchange.getRequest().getPath().value();
    if (!path.startsWith(SOCKET_IO_PATH)) {
      return chain.filter(exchange);
    }

    String jwtToken = extractJwtToken(exchange);
    if (jwtToken == null) {
      log.warn("No JWT token provided for Socket.IO handshake: {}", path);
      return unauthorizedResponse(exchange);
    }

    return validateAndDecodeJwt(jwtToken)
        .flatMap(accountRequest -> {
          exchange.getAttributes().put("accountRequest", accountRequest);
          log.info("JWT decoded for user: {}", accountRequest.id());
          return chain.filter(exchange);
        })
        .onErrorResume(ex -> {
          log.error("JWT validation failed: {}", ex.getMessage());
          return unauthorizedResponse(exchange);
        });
  }

  private String extractJwtToken(ServerWebExchange exchange) {
    List<String> authHeaders = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
    if (authHeaders != null && !authHeaders.isEmpty()) {
      String header = authHeaders.get(0);
      if (header.startsWith("Bearer ")) {
        return header.substring(7);
      }
    }
    return exchange.getRequest().getQueryParams().getFirst("token");
  }

  private Mono<TAccountRequest> validateAndDecodeJwt(String token) {
    return jwtDecoder.decode(token)
        .flatMap(jwt -> {
          return Mono
              .just(TAccountRequest.builder().id(UUID.fromString(jwt.getSubject()))
                  .role(AccountRole.valueOf(jwt.getClaim("role"))).build());
        });
  }

  private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
    return exchange.getResponse().setComplete();
  }
}
