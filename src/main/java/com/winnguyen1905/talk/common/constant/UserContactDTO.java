package com.winnguyen1905.talk.common.constant;

import java.util.UUID;
import lombok.Builder;

@Builder
public record UserContactDTO(
    UUID id,
    UUID userId,
    UUID contactId,
    String firstName,
    String lastName) {
}
