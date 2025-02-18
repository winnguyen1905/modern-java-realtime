package com.winnguyen1905.talk.model.viewmodel;

import lombok.Builder;

@Builder
public record AttachmentVM(
    String id,
    String messageId,
    String thumbUrl,
    String fileUrl) {
}
