package com.winnguyen1905.talk.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class BlockList {
    @Id
    @GeneratedValue
    private UUID id;
    private UUID userId;
    private UUID participantId;
    private Date createdAt = new Date();

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    // Getters and setters
}
