package com.winnguyen1905.talk.model.dto;

import java.time.Instant;
import java.util.UUID;

import com.winnguyen1905.talk.common.constant.ReportStatus;

import lombok.Builder;

@Builder
public record ReportDTO(
    UUID id,
    String title,
    String description,
    ReportStatus status,
    Instant createdAt
) {}
