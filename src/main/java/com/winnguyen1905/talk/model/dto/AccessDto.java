package com.winnguyen1905.talk.model.dto;

import java.util.UUID;

import lombok.Builder;

@Builder
public record AccessDto(UUID userId, UUID deviceId, String token) {
}
