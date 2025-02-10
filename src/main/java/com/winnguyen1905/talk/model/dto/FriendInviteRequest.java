package com.winnguyen1905.talk.model.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class FriendInviteRequest {
  private UUID senderId;
  private UUID receiverId;
}
