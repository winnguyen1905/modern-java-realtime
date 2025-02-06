package com.winnguyen1905.talk.persistance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

import com.winnguyen1905.talk.common.constant.ConversationType;

@Getter
@Setter
@Entity
@SuperBuilder
@Table(name = "conversation")
public class EConversation {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
  public UUID id;

  @Column(name = "conversation_title", nullable = false)
  public String title;

  @Column(name = "conversation_creator_id", nullable = false)
  public UUID creatorId;

  @Column(name = "conversation_channel_id", nullable = false)
  public UUID channelId;

  @Enumerated(EnumType.STRING)
  @Column(name = "conversation_type", nullable = false)
  public ConversationType type;

  @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
  public List<EMessage> messages;

  @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
  public List<EParticipant> participants;

  @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
  public List<EDeletedConversation> deletedConversations;
}
