package com.winnguyen1905.talk.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class UserContact {
    @Id
    @GeneratedValue
    private UUID id;
    private UUID userId;
    private UUID contactId;
    private String firstName = "";
    private String lastName = "";
    private Date createdAt = new Date();
    private Date updatedAt = new Date();

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "contact_id", insertable = false, updatable = false)
    private User contact;

    // Getters and setters
}
