package com.winnguyen1905.talk.persistance.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("friendship")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EFriendship {
  @Id
  private UUID id;

  @Column("user1_id")
  private UUID user1Id;

  @Column("user2_id")
  private UUID user2Id;
}
