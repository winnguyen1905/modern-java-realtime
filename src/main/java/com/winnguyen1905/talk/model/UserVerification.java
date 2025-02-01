package com.winnguyen1905.talk.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class UserVerification {
    @Id
    @GeneratedValue
    private UUID userId;
    private String verificationCode;
    private Date createdAt;

    @OneToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    // Getters and setters
}
