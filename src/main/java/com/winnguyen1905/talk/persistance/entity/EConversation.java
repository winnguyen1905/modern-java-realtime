package com.winnguyen1905.talk.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.winnguyen1905.talk.common.constant.ConversationType;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@Table(name = "conversation")
public class EConversation {
  @Id
  private UUID id;

  @Column("conversation_title")
  private String title;

  @Column("conversation_creator_id")
  private UUID creatorId;

  @Column("conversation_channel_id")
  private UUID channelId;

  @Column("conversation_type")
  private ConversationType type;

  @Transient
  private List<EMessage> messages;

  @Transient
  private List<EParticipant> participants;

  @Transient
  private List<EDeletedConversation> deletedConversations;
}
