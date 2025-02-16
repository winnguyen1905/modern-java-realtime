package com.winnguyen1905.talk.common.constant;

public enum DeviceType {
  APPLE("APPLE"), ANDROID("ANDROID"), WINDOWS("WINDOWS"), MAC("MAC"), LINUX("LINUX");

  private final String value;

  DeviceType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
