package com.winnguyen1905.talk.persistance.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.winnguyen1905.talk.persistance.entity.EUser;

import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<EUser, UUID> {
  Mono<EUser> findByUsername(String username);
}
