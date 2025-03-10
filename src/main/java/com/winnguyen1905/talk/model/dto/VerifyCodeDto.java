package com.winnguyen1905.talk.model.dto;

import java.util.UUID;

public record VerifyCodeDto(String code, UUID userId) {}
