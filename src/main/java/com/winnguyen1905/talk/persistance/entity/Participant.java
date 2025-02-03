package com.winnguyen1905.talk.persistance.entity;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

import com.winnguyen1905.talk.common.constant.ParticipantType;

@Entity
@SuperBuilder
@Table(name = "participant", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "conversation_id", "user_id" })
})
public class Participant {
  @Id
  @GeneratedValue
  @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
  public UUID id;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  public ParticipantType type;

  @ManyToOne
  @JoinColumn(name = "conversation_id", insertable = false, updatable = false)
  public Conversation conversation;

  @ManyToOne
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  public User user;
}
