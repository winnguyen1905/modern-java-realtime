package com.winnguyen1905.talk.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class Participant {
  @Id
  @GeneratedValue
  private UUID id;
  private UUID conversationId;
  private UUID userId;
  @Enumerated(EnumType.STRING)
  private ParticipantType type;
  private Date createdAt = new Date();
  private Date updatedAt = new Date();

  @ManyToOne
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "conversation_id", insertable = false, updatable = false)
  private Conversation conversation;

  // Getters and setters
}
