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
@Table(name = "user_verification")
public class EUserVerification {
  @Id
  @Column("user_id")
  private UUID id;

  @Column("verification_code")
  private String verificationCode;
}
