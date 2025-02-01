package com.winnguyen1905.talk.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "conversation")
public class Conversation {

  @Id
  @GeneratedValue
  @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
  public UUID id;

  @Column(name = "title", nullable = false)
  public String title;

  @Column(name = "creator_id", nullable = false)
  public UUID creatorId;

  @Column(name = "channel_id", nullable = false)
  public UUID channelId;

  @Column(name = "created_at", nullable = false, updatable = false)
  public LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "updated_at")
  public LocalDateTime updatedAt = LocalDateTime.now();

  @Column(name = "deleted_at")
  public LocalDateTime deletedAt;

  @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
  public List<Message> messages;

  @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
  public List<Participant> participants;

  @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
  public List<DeletedConversation> deletedConversations;

  public Conversation() {
  }

  public Conversation(String title, UUID creatorId, UUID channelId) {
    this.title = title;
    this.creatorId = creatorId;
    this.channelId = channelId;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }
}
