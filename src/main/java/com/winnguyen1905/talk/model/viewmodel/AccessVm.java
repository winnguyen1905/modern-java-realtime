package com.winnguyen1905.talk.model.viewmodel;

import java.util.UUID;

import lombok.Builder;

@Builder
public record AccessVm(
  UUID id,
  UUID userId,
  String token,
  UUID deviceId
) {
}
