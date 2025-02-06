package com.winnguyen1905.talk.persistance.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

import com.winnguyen1905.talk.common.constant.MessageType;

import java.time.Instant;

@Entity
@SuperBuilder
@Table(name = "message")
public class EMessage {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
  public UUID id;

  @Default
  @Column(name = "guid", nullable = false, unique = true)
  public String guid = "0000000000000000";

  @Column(name = "conversation_id", nullable = false)
  public UUID conversationId;

  @Column(name = "sender_id", nullable = false)
  public UUID senderId;

  @Enumerated(EnumType.STRING)
  @Column(name = "message_type", nullable = false)
  public MessageType messageType;

  @Default
  @Column(name = "message", nullable = false)
  public String message = "NULL";

  @ManyToOne
  @JoinColumn(name = "conversation_id", insertable = false, updatable = false)
  public EConversation conversation;

  @ManyToOne
  @JoinColumn(name = "sender_id", insertable = false, updatable = false)
  public User user;

  @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
  public List<EAttachment> attachments;

  @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
  public List<EDeletedMessage> deletedMessages;
}
