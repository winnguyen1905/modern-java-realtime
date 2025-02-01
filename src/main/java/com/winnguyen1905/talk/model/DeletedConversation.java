package com.winnguyen1905.talk.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDateTime;

@Entity
public class DeletedConversation {
  @Id
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
