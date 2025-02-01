package com.winnguyen1905.talk.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class Contact {
    @Id
    @GeneratedValue
    private UUID id;
    private String firstName = "";
    private String middleName = "";
    private String lastName = "";
    private String phone;
    private String email;
    private Date createdAt = new Date();

    // Getters and setters
}
