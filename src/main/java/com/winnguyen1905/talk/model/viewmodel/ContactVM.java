package com.winnguyen1905.talk.model.viewmodel;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record ContactVM(
    UUID id,
    String firstName,
    String middleName,
    String lastName,
    String phone,
    String email,
    Instant createdAt) {
}
