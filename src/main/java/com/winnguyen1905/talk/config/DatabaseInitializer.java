package com.winnguyen1905.talk.config;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;

import com.winnguyen1905.talk.persistance.entity.User;
import com.winnguyen1905.talk.persistance.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {
  private final UserRepository userRepository;
  // private final TransactionalOperator transactionalOperator;

  @Override
  public void run(String... args) throws Exception {
    // userRepository.findById(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"))
    //     .flatMap(x -> {
    //       System.out.println(x);
    //       return Mono.empty();
    //     }).subscribe();
    userRepository.save(User.builder()
        // .id(UUID.randomUUID()) // Generating a random UUID
        .firstName("Jose")
        .middleName("M.")
        .lastName("Garcia")
        .phone("123456789088888")
        .email("jose@example.cosadasdm")
        .username("joseg_dsfsdfsdfsdf")
        .password("securepassword") // Normally, hash this
        .isActive(true)
        .isReported(false)
        .isBlocked(false)
        .preferences("{'theme': 'dark', 'notifications': true}")
        .createdAt(Instant.now()) 
        .updatedAt(Instant.now())
        .build())
        .subscribe();
  }

}
