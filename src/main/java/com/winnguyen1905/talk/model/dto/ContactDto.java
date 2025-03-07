package com.winnguyen1905.talk.model.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ContactDto(
    UUID id,
    String email,
    String phone,
    String lastName,
    String firstName,
    String middleName) {
}
