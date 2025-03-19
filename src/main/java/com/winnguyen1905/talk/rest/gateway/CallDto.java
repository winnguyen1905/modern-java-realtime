package com.winnguyen1905.talk.rest.gateway;

import java.util.UUID;

import com.winnguyen1905.talk.common.constant.CallType;

import lombok.Builder;

@Builder
public class CallDto {
  private UUID id;
  private UUID callerId;
  private UUID recieverId;
  private CallType callType;
  private String signal;

  public UUID getId() {
    return id;
  }

  public UUID getCallerId() {
    return callerId;
  }

  public UUID getRecieverId() {
    return recieverId;
  }

  public CallType getCallType() {
    return callType;
  }

  public String getSignal() {
    return signal;
  }
}
