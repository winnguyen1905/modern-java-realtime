package com.winnguyen1905.talk.model.viewmodel;

import lombok.Builder;

import java.util.UUID;

@Builder
public record BlockListVm(UUID id, UUID blockerId, UUID blockedId) {
  public BlockListVm {}
}
