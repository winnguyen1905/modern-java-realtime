package com.winnguyen1905.talk.persistance.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

import com.winnguyen1905.talk.common.constant.MessageType;

import java.time.Instant;

@Entity
@SuperBuilder
@Table(name = "message")
public class Message {
  @Id@GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
  public UUID id;

  @Column(name = "guid", nullable = false, unique = true)
  public String guid;

  @Column(name = "conversation_id", nullable = false)
  public UUID conversationId;

  @Column(name = "sender_id", nullable = false)
  public UUID senderId;

  @Enumerated(EnumType.STRING)
  @Column(name = "message_type", nullable = false)
  public MessageType messageType;

  @Column(name = "message", nullable = false)
  public String message = "";

  @Column(name = "created_at", nullable = false, updatable = false)
  public Instant createdAt = Instant.now();

  @Column(name = "deleted_at")
  public Instant deletedAt;

  @ManyToOne
  @JoinColumn(name = "conversation_id", insertable = false, updatable = false)
  public Conversation conversation;

  @ManyToOne
  @JoinColumn(name = "sender_id", insertable = false, updatable = false)
  public User user;

  @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
  public List<Attachment> attachments;

  @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
  public List<DeletedMessage> deletedMessages;
}
