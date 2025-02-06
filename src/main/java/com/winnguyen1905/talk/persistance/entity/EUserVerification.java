package com.winnguyen1905.talk.persistance.entity;

import java.util.UUID;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Table(name = "user_verification")
public class EUserVerification {
  @Id   
  @GeneratedValue
  @Column(name = "user_id", columnDefinition = "UUID")
  private UUID id;

  @Column(name = "verification_code", nullable = false)
  private String verificationCode;

  @MapsId
  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;
}
