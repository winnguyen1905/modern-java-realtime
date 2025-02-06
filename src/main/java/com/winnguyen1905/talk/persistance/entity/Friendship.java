package com.winnguyen1905.talk.persistance.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("friendship")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Friendship {
  @Id
  private UUID id;
  private UUID user1Id;
  private UUID user2Id;
  private LocalDateTime createdAt;
}
