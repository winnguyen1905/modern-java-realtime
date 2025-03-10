package com.winnguyen1905.talk.model.dto;

import java.io.Serializable;
import java.util.UUID;

public record NotificationDto(
    UUID receiverId, String title, String content) implements Serializable {
}
