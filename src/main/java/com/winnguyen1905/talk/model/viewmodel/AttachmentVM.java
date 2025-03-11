package com.winnguyen1905.talk.model.viewmodel;

import lombok.Builder;

@Builder
public record AttachmentVm(
    String id,
    String messageId,
    String thumbUrl,
    String fileUrl) {
}
