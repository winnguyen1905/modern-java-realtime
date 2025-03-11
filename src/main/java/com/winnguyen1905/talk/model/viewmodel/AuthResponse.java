package com.winnguyen1905.talk.model.viewmodel;

import lombok.Builder; 

@Builder
public record AuthResponse(
  UserVm user,
  TokenPair tokens
) {}
