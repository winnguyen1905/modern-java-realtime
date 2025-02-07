package com.winnguyen1905.talk.persistance.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;
import java.util.UUID;

import com.winnguyen1905.talk.persistance.entity.EUserVerification;

public interface UserVerificationRepository extends R2dbcRepository<EUserVerification, UUID> {
}
