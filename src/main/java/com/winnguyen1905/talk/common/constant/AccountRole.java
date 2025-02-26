package com.winnguyen1905.talk.common.constant;

public enum AccountRole {
  ADMIN("ADMIN"), USER("USER"), GUESS("GUESS");

  String role;

  AccountRole(String role) {
    this.role = role; 
  }
}
