package com.winnguyen1905.talk.persistance.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.winnguyen1905.talk.common.constant.FriendInviteStatus;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("friend_invite")
public class EFriendInvite {
  @Id
  private UUID id;

  @Column("sender_id")
  private UUID senderId;

  @Column("receiver_id")
  private UUID receiverId;

  @Column("friend_invite_status")
  private FriendInviteStatus status;
}
