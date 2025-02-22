package com.winnguyen1905.talk.common.annotation;

import java.io.Serializable;
import java.util.UUID;

import lombok.Builder;

@Builder
public record TAccountRequest(
    UUID id,
    TRole role,
    UUID socketClientId) implements Serializable {

  public record TRole(
      String type) {
  }

  public TAccountRequest {
  }
}
