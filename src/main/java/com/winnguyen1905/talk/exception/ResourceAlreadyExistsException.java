package com.winnguyen1905.talk.exception;

public class ResourceAlreadyExistsException extends RuntimeException {
  public ResourceAlreadyExistsException(String message) {
      super(message);
  }
}
