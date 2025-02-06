package com.winnguyen1905.talk.persistance.entity;

import lombok.experimental.SuperBuilder;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@SuperBuilder
@Table(name = "block_list")
public class EBlockList {
  @Id
  @Column("id")
  private UUID id;

  @Column("user_id")
  private UUID userId;

  @Column("participant_id")
  private UUID participantId;
}
