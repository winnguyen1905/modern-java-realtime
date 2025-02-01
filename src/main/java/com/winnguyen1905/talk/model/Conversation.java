package com.winnguyen1905.talk.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Conversation {
  @Id
  @GeneratedValue
  private UUID id;
  private String title;
  private UUID creatorId;
  private UUID channelId;
  private Date createdAt = new Date();
  private Date updatedAt = new Date();
  private Date deletedAt;

  @OneToMany(mappedBy = "conversation")
  private List<Message> messages;
  @OneToMany(mappedBy = "conversation")
  private List<Participant> participants;
  @OneToMany(mappedBy = "conversation")
  private List<DeletedConversation> deletedConversations;

  // Getters and setters
}
