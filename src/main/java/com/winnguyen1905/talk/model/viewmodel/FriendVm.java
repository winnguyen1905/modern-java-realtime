package com.winnguyen1905.talk.model.viewmodel;

import lombok.Builder;

@Builder
public record FriendVm(
  String username,
  String avatarUrl,
  boolean isOnline,
  boolean isBlocked,
  boolean isFriend
) {
  
}
