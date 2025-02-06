package com.winnguyen1905.talk.model.dto;

import java.util.UUID;

public record ParticipantActionDto(
    UUID userId,
    UUID conversationId) {
}
