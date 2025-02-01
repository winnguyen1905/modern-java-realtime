package com.winnguyen1905.talk.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Device {
    @Id
    @GeneratedValue
    private UUID id;
    private UUID userId;
    private String deviceId;
    private String deviceToken;
    @Enumerated(EnumType.STRING)
    private DeviceType type;
    private Date createdAt = new Date();
    private Date updatedAt = new Date();

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @OneToMany(mappedBy = "device")
    private List<Access> accesses;

    // Getters and setters
}
