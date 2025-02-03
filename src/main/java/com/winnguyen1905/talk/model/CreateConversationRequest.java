package com.winnguyen1905.talk.model;

import java.util.UUID;

public record CreateConversationRequest(
    UUID id,
    String title,
    UUID channelId,
    UUID creatorId,
    UUID receiverId,
    boolean isGroup) {

}
