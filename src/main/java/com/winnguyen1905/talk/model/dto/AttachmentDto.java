package com.winnguyen1905.talk.model.dto;

import lombok.Builder;

@Builder
public record AttachmentDto(
    String id,
    String messageId,
    String thumbUrl,
    String fileUrl) {
}
