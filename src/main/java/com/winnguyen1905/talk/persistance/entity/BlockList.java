package com.winnguyen1905.talk.persistance.entity;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

import java.time.Instant;

@Entity
@SuperBuilder
@Table(name = "block_list")
public class BlockList {

  @Id  @GeneratedValue(strategy = GenerationType.UUID)

  @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
  public UUID id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  public User user;

  @Column(name = "participant_id", nullable = false)
  public UUID participantId;

  @Column(name = "created_at", nullable = false, updatable = false)
  public Instant createdAt = Instant.now();
 
}
