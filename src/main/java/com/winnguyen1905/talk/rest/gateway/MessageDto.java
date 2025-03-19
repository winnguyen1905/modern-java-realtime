package com.winnguyen1905.talk.rest.gateway;

import lombok.Builder;
import lombok.Setter;
import lombok.Builder.Default;

import java.util.UUID;

import com.winnguyen1905.talk.common.constant.MessageStatus;
import com.winnguyen1905.talk.common.constant.MessageType;

@Builder
@Setter
public class MessageDto {
  private UUID senderId;
  private String message;
  private UUID messageId;
  private UUID receiverId;
  private MessageStatus status;
  private MessageType messageType;

  public MessageDto(UUID senderId, String message, UUID messageId, UUID receiverId,
      MessageStatus status, MessageType messageType) {
    this.senderId = senderId;
    this.message = message;
    this.messageId = messageId;
    this.receiverId = receiverId;
    this.status = status != null ? status : MessageStatus.SENT;
    this.messageType = messageType;
  }

  public UUID getSenderId() {
    return senderId;
  }

  public String getMessage() {
    return message;
  }

  public UUID getMessageId() {
    return messageId;
  }

  public UUID getReceiverId() {
    return receiverId;
  }

  public MessageStatus getStatus() {
    return status;
  }

  public MessageType getMessageType() {
    return messageType;
  }
}
