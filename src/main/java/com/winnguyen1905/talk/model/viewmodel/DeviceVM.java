package com.winnguyen1905.talk.model.viewmodel;

import lombok.Builder;
import java.time.Instant;
import java.util.UUID;

import com.winnguyen1905.talk.common.constant.DeviceType;

@Builder
public record DeviceVm(
    UUID id,
    UUID userId,
    UUID deviceId,
    String deviceToken,
    DeviceType type,
    Instant createdAt,
    Instant updatedAt
) {}
