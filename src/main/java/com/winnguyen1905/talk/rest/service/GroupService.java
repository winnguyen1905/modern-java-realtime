package com.winnguyen1905.talk.rest.service;

import org.springframework.stereotype.Service;

import com.winnguyen1905.talk.common.annotation.TAccountRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupService {
  public final void createItem(
    CreateConversationRequest request, TAccountRequest account
  ) {
    // Implement logic to create a new group
  }
}
