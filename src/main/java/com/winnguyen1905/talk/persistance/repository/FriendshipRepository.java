package com.winnguyen1905.talk.persistance.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.winnguyen1905.talk.persistance.entity.EFriendship;

import reactor.core.publisher.Mono;
import java.util.UUID;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface FriendshipRepository extends R2dbcRepository<EFriendship, UUID> {
  Mono<EFriendship> findByUser1IdAndUser2Id(UUID user1Id, UUID user2Id);
  Flux<EFriendship> findAllByUser1IdOrUser2Id(UUID userId1, UUID userId2);
  Mono<Void> deleteByUser1IdAndUser2Id(UUID user1Id, UUID user2Id);
  Mono<Void> deleteByUser1IdOrUser2Id(UUID userId1, UUID userId2);
}
