package com.winnguyen1905.talk.model.dto;

import java.util.UUID;

public record MessageActionDto(
  UUID id,
  UUID userId,
  UUID conversationId,
  String action
) {
  
}
