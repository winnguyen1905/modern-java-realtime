package com.winnguyen1905.talk.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", columnDefinition = "UUID")
  private UUID id;

  @Column(name = "phone", unique = true, nullable = false)
  private String phone;

  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @Column(name = "usernames", unique = true, nullable = false)
  private String usernames;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "first_name", nullable = false, columnDefinition = "varchar(255) default ''")
  private String firstName;

  @Column(name = "middle_name")
  private String middleName;

  @Column(name = "last_name", nullable = false, columnDefinition = "varchar(255) default ''")
  private String lastName;

  @Column(name = "is_active", nullable = false, columnDefinition = "boolean default false")
  private Boolean isActive;

  @Column(name = "is_reported", nullable = false, columnDefinition = "boolean default false")
  private Boolean isReported;

  @Column(name = "is_blocked", nullable = false, columnDefinition = "boolean default false")
  private Boolean isBlocked;

  @Column(name = "preferences")
  private String preferences;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  // Relationships
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Device> devices;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Message> messages;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Participant> participants;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Report> reports;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<DeletedMessage> deletedMessages;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<DeletedConversation> deletedConversations;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Access> accesses;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<UserContact> userContacts;

  @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<UserContact> contacts;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<BlockList> blockLists;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private UserVerification userVerification;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Story> stories;

  @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Friend> friendsRequested;

  @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Friend> friendsReceived;
}
