package com.winnguyen1905.talk.model.dto;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class UserContactDto {
  private UUID userId;
  private UUID contactId;
  private String firstName;
  private String lastName;
}
