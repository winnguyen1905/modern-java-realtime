package com.winnguyen1905.talk.persistance.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import org.hibernate.annotations.UuidGenerator;
import java.time.Instant;
import java.util.UUID;

@Entity
@SuperBuilder
@Table(name = "deleted_conversation")
public class EDeletedConversation {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "message_id")
  private String messageId;

  @Column(name = "user_id")
  private String userId;

  @ManyToOne
  @JoinColumn(name = "message_id", insertable = false, updatable = false)
  private EMessage message;

  @ManyToOne
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  private EUser user;
}
