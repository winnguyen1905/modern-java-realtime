package com.winnguyen1905.talk.persistance.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.winnguyen1905.talk.persistance.entity.EBlockList;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface BlockListRepository extends R2dbcRepository<EBlockList, UUID> {
    Mono<EBlockList> findByBlockerIdAndBlockedId(UUID blockerId, UUID blockedId);
    Flux<EBlockList> findByBlockerId(UUID blockerId);
    Mono<Void> deleteByBlockerIdAndBlockedId(UUID blockerId, UUID blockedId);
}
