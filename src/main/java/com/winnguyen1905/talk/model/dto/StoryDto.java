package com.winnguyen1905.talk.model.dto;

import java.time.Instant;
import java.util.UUID;
import lombok.Builder;

@Builder
public record StoryDto(
    UUID id,
    UUID userId,
    String content,
    Instant expiresAt) {
}
