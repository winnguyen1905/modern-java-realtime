package com.winnguyen1905.talk.persistance.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.winnguyen1905.talk.persistance.entity.EAttachment;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AttachmentRepository extends R2dbcRepository<EAttachment, String> {
    Flux<EAttachment> findByMessageId(String messageId);
}
