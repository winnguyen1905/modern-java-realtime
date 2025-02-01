package com.winnguyen1905.talk.model;

import jakarta.persistence.*;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "contact")
public class Contact {

  @Id
  @GeneratedValue
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
  public LocalDateTime createdAt = LocalDateTime.now();

  public Contact() {
  }

  public Contact(String firstName, String middleName, String lastName, String phone, String email) {
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.phone = phone;
    this.email = email;
    this.createdAt = LocalDateTime.now();
  }
}
