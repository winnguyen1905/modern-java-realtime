package com.winnguyen1905.talk.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "deleted_message")
public class DeletedMessage {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "message_id")
  private UUID messageId;

  @Column(name = "user_id")
  private UUID userId;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @ManyToOne
  @JoinColumn(name = "message_id", insertable = false, updatable = false)
  private Message message;

  @ManyToOne
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  private User user;
}
