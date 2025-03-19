package com.winnguyen1905.talk.persistance.repository;

import java.util.UUID;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.winnguyen1905.talk.persistance.entity.EParticipant;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ParticipantRepository extends ReactiveCrudRepository<EParticipant, UUID> {
  Flux<EParticipant> findAllByUserId(UUID userId);
  @Query(value = "SELECT conversation_id FROM participant WHERE user_id = :userId")
  Flux<UUID> findAllConversationIdByUserId(UUID userId);
  Flux<EParticipant> findByConversationId(UUID conversationId);
  Mono<EParticipant> findByConversationIdAndUserId(UUID conversationId, UUID userId);
}
