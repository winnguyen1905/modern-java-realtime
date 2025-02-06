package com.winnguyen1905.talk.persistance.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@Entity
@SuperBuilder
@Table(name = "friend", uniqueConstraints = @UniqueConstraint(columnNames = { "requester_id", "receiver_id" }))
public class EFriend {
  @Id  @GeneratedValue(strategy = GenerationType.UUID)

  @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
  public UUID id;

  @Column(name = "requester_id", nullable = false)
  public UUID requesterId;

  @Column(name = "receiver_id", nullable = false)
  public UUID receiverId;

  @Column(name = "status", nullable = false)
  public String status = "PENDING"; // PENDING, ACCEPTED, REJECTED

  @Column(name = "created_at", nullable = false, updatable = false)
  public Instant createdAt = Instant.now();

  @ManyToOne
  @JoinColumn(name = "requester_id", insertable = false, updatable = false)
  public User requester;

  @ManyToOne
  @JoinColumn(name = "receiver_id", insertable = false, updatable = false)
  public User receiver;
 
}
