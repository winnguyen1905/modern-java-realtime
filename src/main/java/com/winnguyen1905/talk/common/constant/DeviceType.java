package com.winnguyen1905.talk.common.constant;

public enum DeviceType {
  APPLE("apple");

  private final String value;

  DeviceType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
