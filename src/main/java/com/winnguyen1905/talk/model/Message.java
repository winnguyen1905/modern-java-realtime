package com.winnguyen1905.talk.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class Message {

  @Id
  @GeneratedValue
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
  public LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "deleted_at")
  public LocalDateTime deletedAt;

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

  public Message() {
  }

  public Message(String guid, UUID conversationId, UUID senderId, MessageType messageType, String message) {
    this.guid = guid;
    this.conversationId = conversationId;
    this.senderId = senderId;
    this.messageType = messageType;
    this.message = message;
    this.createdAt = LocalDateTime.now();
  }
}
