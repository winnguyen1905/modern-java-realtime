package com.winnguyen1905.talk.model.dto;

import lombok.Builder;

import java.util.UUID;

import com.winnguyen1905.talk.common.constant.DeviceType;

@Builder
public record DeviceDTO(
    UUID userId,
    UUID deviceId,
    String deviceToken,
    DeviceType type
) {}
