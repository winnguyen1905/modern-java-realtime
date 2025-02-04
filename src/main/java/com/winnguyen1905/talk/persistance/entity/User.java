package com.winnguyen1905.talk.persistance.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;
import java.time.Instant;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import org.springframework.data.relational.core.mapping.Column;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.OneToMany;
// import jakarta.persistence.OneToOne;
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
  // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch =
  // FetchType.LAZY)
  @Builder.Default
  private List<Device> devices = new ArrayList<>();

  @Transient
  // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch =
  // FetchType.LAZY)
  @Builder.Default
  private List<Message> messages = new ArrayList<>();

  @Transient
  // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch =
  // FetchType.LAZY)
  @Builder.Default
  private List<Participant> participants = new ArrayList<>();

  @Transient
  // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch =
  // FetchType.LAZY)
  @Builder.Default
  private List<Report> reports = new ArrayList<>();

  @Transient
  // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch =
  // FetchType.LAZY)
  @Builder.Default
  private List<DeletedMessage> deletedMessages = new ArrayList<>();

  @Transient
  // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch =
  // FetchType.LAZY)
  @Builder.Default
  private List<DeletedConversation> deletedConversations = new ArrayList<>();

  @Transient
  // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch =
  // FetchType.LAZY)
  @Builder.Default
  private List<Access> accesses = new ArrayList<>();

  @Transient
  // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch =
  // FetchType.LAZY)
  @Builder.Default
  private List<UserContact> userContacts = new ArrayList<>();

  @Transient
  // @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch =
  // FetchType.LAZY)
  @Builder.Default
  private List<UserContact> contacts = new ArrayList<>();

  @Transient
  // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch =
  // FetchType.LAZY)
  @Builder.Default
  private List<BlockList> blockLists = new ArrayList<>();

  @Transient
  // @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch =
  // FetchType.LAZY)
  private UserVerification userVerification;

  @Transient
  // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch =
  // FetchType.LAZY)
  @Builder.Default
  private List<Story> stories = new ArrayList<>();

  @Transient
  // @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL, fetch =
  // FetchType.LAZY)
  @Builder.Default
  private List<Friend> friendsRequested = new ArrayList<>();

  @Transient
  // @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, fetch =
  // FetchType.LAZY)
  @Builder.Default
  private List<Friend> friendsReceived = new ArrayList<>();
}
