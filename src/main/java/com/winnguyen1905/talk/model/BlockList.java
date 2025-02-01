package com.winnguyen1905.talk.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

import java.time.LocalDateTime;

@Entity
@Table(name = "block_list")
public class BlockList {

  @Id
  @GeneratedValue
  @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
  public UUID id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  public User user;

  @Column(name = "participant_id", nullable = false)
  public UUID participantId;

  @Column(name = "created_at", nullable = false, updatable = false)
  public LocalDateTime createdAt = LocalDateTime.now();

  public BlockList() {
  }

  public BlockList(User user, UUID participantId) {
    this.user = user;
    this.participantId = participantId;
    this.createdAt = LocalDateTime.now();
  }
}
