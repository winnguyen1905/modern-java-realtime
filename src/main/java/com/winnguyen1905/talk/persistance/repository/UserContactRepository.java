package com.winnguyen1905.talk.persistance.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.winnguyen1905.talk.persistance.entity.EUserContact;

import reactor.core.publisher.Flux;
import java.util.UUID;

public interface UserContactRepository extends R2dbcRepository<EUserContact, UUID> {
  Flux<EUserContact> findByUserId(UUID userId);
}
