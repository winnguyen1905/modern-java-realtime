package com.winnguyen1905.talk.persistance.entity;

import java.time.Instant;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
public class Attachment {
  @Id  
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "message_id")
  private String messageId;

  @Column(name = "thumb_url")
  private String thumbUrl;

  @Column(name = "file_url")
  private String fileUrl;

  @Column(name = "created_at")
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;

  @ManyToOne
  @JoinColumn(name = "message_id", insertable = false, updatable = false)
  private Message message;

  @PrePersist
  protected void onCreate() {
    createdAt = Instant.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = Instant.now();
  }
}
