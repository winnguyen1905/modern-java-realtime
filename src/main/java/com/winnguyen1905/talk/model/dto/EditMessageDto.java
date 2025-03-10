package com.winnguyen1905.talk.model.dto;

import java.util.UUID;

public record EditMessageDto(
  UUID id,
  String message
) {
  
}
