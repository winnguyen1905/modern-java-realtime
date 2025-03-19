package com.winnguyen1905.talk.persistance.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.winnguyen1905.talk.persistance.entity.EDevice;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface DeviceRepository extends R2dbcRepository<EDevice, UUID> {
  Flux<EDevice> findByUserId(UUID userId);
}
