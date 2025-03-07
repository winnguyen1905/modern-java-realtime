package com.winnguyen1905.talk.model.dto;

import java.util.UUID;

public record ConversationDto(
    UUID id,
    String title,
    UUID channelId,
    UUID creatorId,
    UUID receiverId,
    boolean isGroup) {
}
