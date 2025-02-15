package com.winnguyen1905.talk.persistance.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.winnguyen1905.talk.persistance.entity.EReport;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface ReportRepository extends R2dbcRepository<EReport, UUID> {
  Flux<EReport> findByUserId(UUID userId);
}
