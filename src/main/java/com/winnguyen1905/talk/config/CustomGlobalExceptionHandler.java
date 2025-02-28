package com.winnguyen1905.talk.config;


import java.time.Instant;
import java.util.Map;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;

import com.winnguyen1905.talk.exception.ResourceNotFoundException;

import reactor.core.publisher.Mono;

@Component
public class CustomGlobalExceptionHandler extends AbstractErrorWebExceptionHandler {

  public CustomGlobalExceptionHandler(
      ErrorAttributes errorAttributes,
      WebProperties.Resources resources,
      ApplicationContext applicationContext,
      ServerCodecConfigurer configurer) {
    super(errorAttributes, resources, applicationContext);
    this.setMessageWriters(configurer.getWriters());
  }

  @Override
  protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
    return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
  }

  private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
    ErrorAttributeOptions options = ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE);
    Map<String, Object> errorPropertiesMap = getErrorAttributes(request, options);
    Throwable throwable = getError(request);
    HttpStatusCode httpStatus = determineHttpStatus(throwable);

    errorPropertiesMap.put("timestamp", Instant.now().toString());
    errorPropertiesMap.put("status", httpStatus.value());
    errorPropertiesMap.put("error", HttpStatus.valueOf(httpStatus.value()).getReasonPhrase());
    errorPropertiesMap.put("message", throwable.getMessage());
    errorPropertiesMap.put("path", request.path());

    return ServerResponse.status(httpStatus)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(errorPropertiesMap);
  }

  private HttpStatusCode determineHttpStatus(Throwable throwable) {
    if (throwable instanceof ResponseStatusException responseStatusException) {
      return responseStatusException.getStatusCode();
    } else if (throwable instanceof JwtException || throwable instanceof BadCredentialsException || throwable instanceof UsernameNotFoundException) {
      return HttpStatus.UNAUTHORIZED;
    } else if (throwable instanceof ResourceNotFoundException) {
      return HttpStatus.UNPROCESSABLE_ENTITY;
    } else {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }
  }
}
