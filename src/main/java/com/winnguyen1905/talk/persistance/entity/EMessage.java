package com.winnguyen1905.talk.persistance.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.winnguyen1905.talk.common.constant.MessageType;

import java.util.UUID;

@Table("message")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EMessage {
  @Id
  private UUID id;

  @Column("is_seen")
  private Boolean isSeen;

  @Column("guid")
  private String guid;

  @Column("conversation_id")
  private UUID conversationId;

  @Column("sender_id")
  private UUID senderId;

  @Column("message_type")
  private MessageType messageType;

  @Column("message")
  private String message;
}
