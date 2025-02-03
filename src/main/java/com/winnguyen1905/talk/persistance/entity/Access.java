package com.winnguyen1905.talk.persistance.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@SuperBuilder
@Table(name = "access")
public class Access {

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

  // Relationships
  @ManyToOne
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "device_id", insertable = false, updatable = false)
  private Device device;
}
