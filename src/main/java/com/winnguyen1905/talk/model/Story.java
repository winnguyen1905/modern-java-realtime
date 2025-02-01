package com.winnguyen1905.talk.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "story")
public class Story {
  @Id
  @GeneratedValue
  @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
  public UUID id;

  @Column(name = "user_id", nullable = false)
  public UUID userId;

  @Column(name = "content", nullable = false, columnDefinition = "TEXT")
  public String content;

  @Column(name = "created_at", nullable = false, updatable = false)
  public LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "updated_at", nullable = false)
  public LocalDateTime updatedAt = LocalDateTime.now();

  @Column(name = "expires_at", nullable = false)
  public LocalDateTime expiresAt;

  @ManyToOne
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  public User user;

  public Story() {
  }

  public Story(UUID userId, String content, LocalDateTime expiresAt) {
    this.userId = userId;
    this.content = content;
    this.expiresAt = expiresAt;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }
}
