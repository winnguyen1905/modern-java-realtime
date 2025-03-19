package com.winnguyen1905.talk.rest.gateway;

import java.time.Instant;
import java.util.UUID;

import com.winnguyen1905.talk.common.constant.CallStatus;

import lombok.Builder;

@Builder
public class CallState {
  private UUID callerId;
  private UUID receiverId;
  private CallStatus status;
  private Instant startTime;

  public CallState(UUID callerId, UUID receiverId, CallStatus status, Instant startTime) {
    this.callerId = callerId;
    this.receiverId = receiverId;
    this.status = status;
    this.startTime = startTime;
  }

  public UUID getCallerId() {
    return callerId;
  }

  public UUID getReceiverId() {
    return receiverId;
  }

  public CallStatus getStatus() {
    return status;
  }

  public Instant getStartTime() {
    return startTime;
  }
}
