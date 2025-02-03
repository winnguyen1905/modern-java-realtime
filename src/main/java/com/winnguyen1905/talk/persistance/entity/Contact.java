package com.winnguyen1905.talk.persistance.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;
import java.time.Instant;

@Entity
@SuperBuilder
@Table(name = "contact")
public class Contact {

  @Id    @GeneratedValue(strategy = GenerationType.UUID)

  @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
  public UUID id;

  @Column(name = "first_name", nullable = false)
  public String firstName = "";

  @Column(name = "middle_name")
  public String middleName;

  @Column(name = "last_name", nullable = false)
  public String lastName = "";

  @Column(name = "phone", nullable = false)
  public String phone;

  @Column(name = "email", nullable = false)
  public String email;

  @Column(name = "created_at", nullable = false, updatable = false)
  public Instant createdAt = Instant.now();
 
}
