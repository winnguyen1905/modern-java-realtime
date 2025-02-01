package com.winnguyen1905.talk.config;

import java.util.UUID;
import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import com.winnguyen1905.talk.common.annotation.TAccountRequest;

@Configuration
public class AccountRequestConfig {
  @Bean
  Function<UserDetails, TAccountRequest> fetchUser() {
    return (principal -> {
      return TAccountRequest.builder()
          .id(UUID.fromString(principal.getUsername())).build();
    });
  }
}
