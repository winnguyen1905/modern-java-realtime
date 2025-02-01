package com.winnguyen1905.talk.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class Access {
    @Id
    @GeneratedValue
    private UUID id;
    private UUID userId;
    private UUID deviceId;
    private String token;
    private Date createdAt = new Date();
    private Date deletedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "device_id", insertable = false, updatable = false)
    private Device device;

    // Getters and setters
}
