package com.winnguyen1905.talk.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;

import com.winnguyen1905.talk.auth.CustomUserDetails;
import com.winnguyen1905.talk.persistance.repository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class ReactiveAuthenticationConfig {
  @Bean
  ReactiveAuthenticationManager reactiveAuthenticationManager(
      ReactiveUserDetailsService userDetailsService,
      PasswordEncoder passwordEncoder) {
    var authManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
    authManager.setPasswordEncoder(passwordEncoder);
    return authManager;
  }

  @Bean("reactiveUserDetailsService")
  ReactiveUserDetailsService reactiveUserDetailsService(UserRepository userRepository) {
    return username -> userRepository.findByUsername(username)
        // .switchIfEmpty(Mono.error(new UsernameNotFoundException("Not found user by
        // username " + username)))
        .map(user -> CustomUserDetails.builder()
            .id(user.getId())
            // .role(user.getRole())
            // .type(user.getType())
            .email(user.getEmail())
            .phone(user.getPhone())
            // .status(user.getStatus())
            .lastName(user.getLastName())
            .username(user.getUsername())
            .password(user.getPassword())
            .firstName(user.getFirstName())
            .build());
  }
}
