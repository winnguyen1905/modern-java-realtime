package com.winnguyen1905.talk.persistance.entity;

import java.util.Date;
import java.util.UUID;

import lombok.*;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Data
@SuperBuilder
@Table(name = "access")
public class EAccess {
  @Id
  @Column("id")
  private UUID id;

  @Column("user_id")
  private UUID userId;

  @Column("device_id") 
  private UUID deviceId;

  @Column("token")
  private String token;

  @Transient
  private EUser user;

  @Transient 
  private EDevice device;
}
