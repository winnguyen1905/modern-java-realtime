package com.winnguyen1905.talk.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public record AuthPermission(String name, String code, String apiPath, String method, String module) {}
