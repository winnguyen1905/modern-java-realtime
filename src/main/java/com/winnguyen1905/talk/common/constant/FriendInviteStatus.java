package com.winnguyen1905.talk.common.constant;

import lombok.Getter;

@Getter
public enum FriendInviteStatus {
  PENDING("pending"),
  ACCEPTED("accepted"),
  REJECTED("rejected");

  private final String status;

  FriendInviteStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return status;
  }
}
