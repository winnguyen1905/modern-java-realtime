package com.winnguyen1905.talk.model.viewmodel;

import java.time.Instant;
import java.util.UUID;

import com.winnguyen1905.talk.common.constant.MessageType;

import lombok.Builder;

@Builder
public record MessageVm(
    UUID id,
    String guid,
    UUID receiverId,
    UUID senderId,
    String message,
    MessageType messageType,
    Instant createdAt) {
}
