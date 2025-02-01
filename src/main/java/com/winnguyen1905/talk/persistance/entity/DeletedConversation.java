package com.winnguyen1905.talk.persistance.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDateTime;

@Entity
@SuperBuilder
@Table(name = "deleted_conversation")
public class DeletedConversation {
  @Id   @GeneratedValue

  @UuidGenerator
  private String id;

  @Column(name = "message_id")
  private String messageId;

  @Column(name = "user_id")
  private String userId;

  @Column(name = "created_at")
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @ManyToOne
  @JoinColumn(name = "message_id", insertable = false, updatable = false)
  private Message message;

  @ManyToOne
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  private User user;

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }
}
