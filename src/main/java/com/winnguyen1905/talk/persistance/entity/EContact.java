package com.winnguyen1905.talk.persistance.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contact")
public class EContact {

  @Id
  private UUID id;

  @Column("first_name")
  private String firstName;

  @Column("middle_name")
  private String middleName;

  @Column("last_name")
  private String lastName;

  @Column("phone")
  private String phone;

  @Column("email")
  private String email;

  @Column("created_at")
  private Instant createdAt;
}
