package com.winnguyen1905.talk.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BlockListDto {
  private UUID blockerId;
  private UUID blockedId;
}
