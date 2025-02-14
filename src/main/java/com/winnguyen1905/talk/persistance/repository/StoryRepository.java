package com.winnguyen1905.talk.persistance.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.winnguyen1905.talk.persistance.entity.EStory;

import reactor.core.publisher.Flux;
import java.time.Instant;
import java.util.UUID;

public interface StoryRepository extends R2dbcRepository<EStory, UUID> {
  Flux<EStory> findByUserId(UUID userId);
  Flux<EStory> findByExpiresAtAfter(Instant now);
}
