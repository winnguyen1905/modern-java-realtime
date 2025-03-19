package com.winnguyen1905.talk.persistance.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@Entity
@SuperBuilder
@Table(name = "deleted_message")
public class EDeletedMessage {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "message_id")
  private UUID messageId;

  @Column(name = "user_id")
  private UUID userId;

  @Column(name = "created_at")
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;

  @ManyToOne
  @JoinColumn(name = "message_id", insertable = false, updatable = false)
  private EMessage message;

  @ManyToOne
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  private EUser user;
}
