package com.winnguyen1905.talk.persistance.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_contact")
public class EUserContact {
  @Id
  private UUID id;

  @Column("user_id")
  private UUID userId;

  @Column("contact_id")
  private UUID contactId;

  @Column("first_name")
  private String firstName;

  @Column("last_name")
  private String lastName;
}
