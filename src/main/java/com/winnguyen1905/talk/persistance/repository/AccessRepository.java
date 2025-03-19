package com.winnguyen1905.talk.persistance.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.winnguyen1905.talk.persistance.entity.EAccess;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface AccessRepository extends R2dbcRepository<EAccess, UUID> {
  Flux<EAccess> findByUserId(UUID userId);
  Mono<EAccess> findByUserIdAndDeviceId(UUID userId, UUID deviceId);
  Mono<Void> deleteByUserIdAndDeviceId(UUID userId, UUID deviceId);
}
