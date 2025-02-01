package com.winnguyen1905.talk.model;

import java.util.UUID;
import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_verification")
public class UserVerification {
  @Id
  @Column(name = "user_id", columnDefinition = "UUID")
  private UUID userId;

  @Column(name = "verification_code", nullable = false)
  private String verificationCode;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @OneToOne
  @MapsId
  @JoinColumn(name = "user_id")
  private User user;
}
