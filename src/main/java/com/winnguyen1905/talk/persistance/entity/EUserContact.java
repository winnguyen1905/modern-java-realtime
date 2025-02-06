package com.winnguyen1905.talk.persistance.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@Entity
@SuperBuilder
@Table(name = "user_contact", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "contact_id" }))
public class EUserContact {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
  public UUID id;

  @Column(name = "user_id", nullable = false)
  public UUID userId;

  @Column(name = "contact_id", nullable = false)
  public UUID contactId;

  @Default
  @Column(name = "first_name", nullable = false)
  public String firstName = "";

  @Default
  @Column(name = "last_name", nullable = false)
  public String lastName = "";

  @ManyToOne
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  public User user;

  @ManyToOne
  @JoinColumn(name = "contact_id", insertable = false, updatable = false)
  public User contact;
}
