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
  @org.springframework.data.relational.core.mapping.Column("id")
  private UUID id;

  @org.springframework.data.relational.core.mapping.Column("user_id")
  private UUID userId;

  @org.springframework.data.relational.core.mapping.Column("device_id") 
  private UUID deviceId;

  @org.springframework.data.relational.core.mapping.Column("token")
  private String token;

  @Transient
  private User user;

  @Transient 
  private EDevice device;
}
