package com.winnguyen1905.talk.persistance.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;
import java.time.Instant;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.persistence.Entity;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.annotation.Transient;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user", schema = "public")
public class User {
  @Id
  @Column("id")
  private UUID id;

  @Column("phone")
  private String phone;

  @Column("email")
  private String email;

  @Column("username")
  private String username;

  @Column("password")
  private String password;

  @Column("first_name")
  private String firstName;

  @Column("middle_name")
  private String middleName;

  @Column("last_name")
  private String lastName;

  @Column("is_active")
  private Boolean isActive;

  @Column("is_reported")
  private Boolean isReported;

  @Column("is_blocked")
  private Boolean isBlocked;

  @Column("preferences")
  private String preferences;

  @Column("updated_at")
  private Instant updatedAt;

  @Column("created_at")
  private Instant createdAt;

  // Relationships
  @Transient

  @Builder.Default
  private List<EDevice> devices = new ArrayList<>();

  @Transient

  @Builder.Default
  private List<EMessage> messages = new ArrayList<>();

  @Transient

  @Builder.Default
  private List<EParticipant> participants = new ArrayList<>();

  @Transient

  @Builder.Default
  private List<EReport> reports = new ArrayList<>();

  @Transient

  @Builder.Default
  private List<EDeletedMessage> deletedMessages = new ArrayList<>();

  @Transient

  @Builder.Default
  private List<EDeletedConversation> deletedConversations = new ArrayList<>();

  @Transient

  @Builder.Default
  private List<EAccess> accesses = new ArrayList<>();

  @Transient

  @Builder.Default
  private List<EUserContact> userContacts = new ArrayList<>();

  @Transient

  @Builder.Default
  private List<EUserContact> contacts = new ArrayList<>();

  @Transient

  @Builder.Default
  private List<EBlockList> blockLists = new ArrayList<>();

  @Transient
  private EUserVerification userVerification;

  @Transient
  @Builder.Default
  private List<EStory> stories = new ArrayList<>();

  @Transient
  @Builder.Default
  private List<EFriend> friendsRequested = new ArrayList<>();

  @Transient
  @Builder.Default
  private List<EFriend> friendsReceived = new ArrayList<>();
}
