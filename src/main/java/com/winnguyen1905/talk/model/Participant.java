package com.winnguyen1905.talk.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "participant", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "conversation_id", "user_id" })
})
public class Participant {

  @Id
  @GeneratedValue
  @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
  public UUID id;

  @Column(name = "conversation_id", nullable = false)
  public UUID conversationId;

  @Column(name = "user_id", nullable = false)
  public UUID userId;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  public ParticipantType type;

  @Column(name = "created_at", nullable = false, updatable = false)
  public LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "updated_at")
  public LocalDateTime updatedAt;

  @ManyToOne
  @JoinColumn(name = "conversation_id", insertable = false, updatable = false)
  public Conversation conversation;

  @ManyToOne
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  public User user;

  public Participant() {
  }

  public Participant(UUID conversationId, UUID userId, ParticipantType type) {
    this.conversationId = conversationId;
    this.userId = userId;
    this.type = type;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }
}
