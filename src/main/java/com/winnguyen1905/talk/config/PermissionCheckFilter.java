package com.winnguyen1905.talk.config;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.winnguyen1905.talk.auth.SecurityUtils;

import reactor.core.publisher.Mono;

@Component
public class PermissionCheckFilter 
implements WebFilter
 {

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    String requestPath = exchange.getRequest().getPath().toString();
    String requestMethod = exchange.getRequest().getMethod().toString();

    if (isWhitelisted(requestPath))
      return chain
          .filter(
              exchange.mutate().request(
                  exchange.getRequest().mutate().header("x-auth0-user-email", "").build())
                  .build());

    return SecurityUtils.getCurrentUsersPermissions()
        .filter(permission -> permission.apiPath().equals(requestPath) && permission.method().equals(requestMethod)
            || permission.apiPath().equals("/api/**"))
        .hasElements()
        .flatMap(hasPermission -> {
          if (hasPermission) {
            return chain
                .filter(
                    exchange.mutate().request(
                        exchange.getRequest().mutate().header("x-auth0-user-email", "").build())
                        .build());
          }
          exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
          return exchange.getResponse().setComplete();
        }).then(
            Mono.fromRunnable(
                () -> {
                  // postsWebSocketHandler.broadcast(
                  // WSEvent.builder()
                  // .message(
                  // String.format(
                  // "%s is trying the API %s",
                  // userInfo.getEmail(),
                  // request.getURI()))
                  // .build());
                }));
  }

  private boolean isWhitelisted(String path) {
    for (String pattern : SecurityConfig.whiteList) {
      if (pathMatches(pattern, path)) {
        return true;
      }
    }
    return false;
  }

  private boolean pathMatches(String pattern, String path) {
    String regexPattern = pattern.replace("**", ".*");
    return path.matches(regexPattern);
  }
}
