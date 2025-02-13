package com.winnguyen1905.talk.persistance.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.winnguyen1905.talk.persistance.entity.EContact;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ContactRepository extends R2dbcRepository<EContact, UUID> {
  Flux<EContact> findByFirstNameContainingIgnoreCase(String firstName);
}
