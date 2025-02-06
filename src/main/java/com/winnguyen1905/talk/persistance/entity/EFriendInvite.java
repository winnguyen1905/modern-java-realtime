package com.winnguyen1905.talk.persistance.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.winnguyen1905.talk.common.constant.FriendInviteStatus;

import java.util.UUID;

@Table("friend_invite")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EFriendInvite {
  @Id
  private UUID id;
  private UUID senderId;
  private UUID receiverId;
  private FriendInviteStatus status;
}
