package com.winnguyen1905.talk.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_contact", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "contact_id" }))
public class UserContact {

  @Id
  @GeneratedValue
  @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
  public UUID id;

  @Column(name = "user_id", nullable = false)
  public UUID userId;

  @Column(name = "contact_id", nullable = false)
  public UUID contactId;

  @Column(name = "first_name", nullable = false)
  public String firstName = "";

  @Column(name = "last_name", nullable = false)
  public String lastName = "";

  @Column(name = "created_at", nullable = false, updatable = false)
  public LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "updated_at", nullable = false)
  public LocalDateTime updatedAt = LocalDateTime.now();

  @ManyToOne
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  public User user;

  @ManyToOne
  @JoinColumn(name = "contact_id", insertable = false, updatable = false)
  public User contact;

  public UserContact() {
  }

  public UserContact(UUID userId, UUID contactId, String firstName, String lastName) {
    this.userId = userId;
    this.contactId = contactId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }
}
