package com.winnguyen1905.talk.persistance.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.winnguyen1905.talk.common.constant.ParticipantType;

@Getter
@Setter
@SuperBuilder
@Table(name = "participant"
// , uniqueConstraints = {
//     @UniqueConstraint(columnNames = { "conversation_id", "user_id" })
// }
)
public class EParticipant {
  @Id
  private UUID id;

  @Column("participant_type")
  private ParticipantType type;

  @Column("user_id")
  private UUID userId;
  
  @Column("conversation_id")
  private UUID conversationId;

  @Transient
  private EConversation conversation;

  @Transient
  private EUser user;
}
