package com.winnguyen1905.talk.model.viewmodel;

import lombok.Builder;

import java.util.UUID;

@Builder
public record BlockListVM(UUID id, UUID blockerId, UUID blockedId) {
  public BlockListVM {}
}
