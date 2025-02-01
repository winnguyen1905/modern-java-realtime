package com.winnguyen1905.talk.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Message {
    @Id
    @GeneratedValue
    private UUID id;
    private String guid;
    private UUID conversationId;
    private UUID senderId;
    @Enumerated(EnumType.STRING)
    private MessageType messageType;
    private String message = "";
    private Date createdAt = new Date();
    private Date deletedAt;

    @ManyToOne
    @JoinColumn(name = "conversation_id", insertable = false, updatable = false)
    private Conversation conversation;

    @ManyToOne
    @JoinColumn(name = "sender_id", insertable = false, updatable = false)
    private User user;

    @OneToMany(mappedBy = "message")
    private List<Attachment> attachments;
    @OneToMany(mappedBy = "message")
    private List<DeletedMessage> deletedMessages;

    // Getters and setters
}
