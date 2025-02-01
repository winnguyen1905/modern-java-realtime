package com.winnguyen1905.talk.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class User {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(unique = true)
    private String phone;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String usernames;
    private String password;
    private String firstName = "";
    private String middleName = "";
    private String lastName = "";
    private boolean isActive = false;
    private boolean isReported = false;
    private boolean isBlocked = false;
    private String preferences = "";
    private Date createdAt = new Date();
    private Date updatedAt = new Date();

    @OneToMany(mappedBy = "user")
    private List<Device> devices;
    @OneToMany(mappedBy = "user")
    private List<Message> messages;
    @OneToMany(mappedBy = "user")
    private List<Participant> participants;
    @OneToMany(mappedBy = "user")
    private List<Report> reports;
    @OneToMany(mappedBy = "user")
    private List<DeletedMessage> deletedMessages;
    @OneToMany(mappedBy = "user")
    private List<DeletedConversation> deletedConversations;
    @OneToMany(mappedBy = "user")
    private List<Access> accesses;
    @OneToMany(mappedBy = "user")
    private List<UserContact> userContacts;
    @OneToMany(mappedBy = "contact")
    private List<UserContact> contacts;
    @OneToMany(mappedBy = "user")
    private List<BlockList> blockLists;
    @OneToOne(mappedBy = "user")
    private UserVerification userVerification;
    @OneToMany(mappedBy = "user")
    private List<Story> stories;
    @OneToMany(mappedBy = "requester")
    private List<Friend> friendsRequested;
    @OneToMany(mappedBy = "receiver")
    private List<Friend> friendsReceived;

    // Getters and setters
}
