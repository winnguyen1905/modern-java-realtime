package com.winnguyen1905.talk.common.constant;

public enum ConversationType {
  DIALOGUE("dialogue "), POLYLOGUE("polylogue");

  private final String value;

  ConversationType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
