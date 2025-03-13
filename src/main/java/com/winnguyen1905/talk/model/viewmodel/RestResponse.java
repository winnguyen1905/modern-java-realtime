package com.winnguyen1905.talk.model.viewmodel;

import lombok.Builder;

@Builder
public record RestResponse<T>(
  T data,
  String error,
  Object message,
  Integer statusCode
) {}
