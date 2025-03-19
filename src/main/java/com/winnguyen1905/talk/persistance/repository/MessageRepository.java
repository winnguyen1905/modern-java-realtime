package com.winnguyen1905.talk.persistance.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.winnguyen1905.talk.persistance.entity.EMessage;

import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MessageRepository extends R2dbcRepository<EMessage, UUID> {
  Mono<EMessage> findByIdAndSenderId(UUID messageId, UUID senderId);
  Flux<EMessage> findByConversationId(UUID conversationId);
  Flux<EMessage> findBySenderId(UUID senderId);
}
