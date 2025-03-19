package com.winnguyen1905.talk.persistance.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@SuperBuilder
@Table(name = "block_list")
public class EBlockList {
  @Id
  private UUID id;

  @Column("blocker_id")
  private UUID blockerId;

  @Column("blocked_id")
  private UUID blockedId;
}
