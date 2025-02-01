package com.winnguyen1905.talk.persistance.entity;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@SuperBuilder
@Table(name = "story")
public class Story {
  @Id  
  @GeneratedValue(strategy = GenerationType.UUID)

  @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
  public UUID id;

  @Column(name = "user_id", nullable = false)
  public UUID userId;

  @Column(name = "content", nullable = false, columnDefinition = "TEXT")
  public String content;

  @Column(name = "expires_at", nullable = false)
  public LocalDateTime expiresAt;

  @ManyToOne
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  public User user;
}
