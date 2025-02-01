package com.winnguyen1905.talk.model;

import jakarta.persistence.*;

import jakarta.persistence.Entity;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "access")
public class EAccess {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", columnDefinition = "UUID")
  private UUID id;

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "device_id", nullable = false)
  private UUID deviceId;

  @Column(name = "token", nullable = false)
  private String token;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  // Relationships
  @ManyToOne
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "device_id", insertable = false, updatable = false)
  private EDevice device;
}
