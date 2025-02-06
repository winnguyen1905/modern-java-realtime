package com.winnguyen1905.talk.model.dto;

import java.util.UUID;

public record AddMessageDto(
  String content,
  UUID senderId,
  UUID conversationId,
  String timestamp,
  String type
) {
  
}
