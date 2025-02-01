package com.winnguyen1905.talk.common.annotation;

import java.util.UUID;

import lombok.Builder;

@Builder
public record TAccountRequest(
    UUID id,
    TRole role
) {
  
  public record TRole(
    String type
  ) {
  }

  public TAccountRequest {
  }
}
