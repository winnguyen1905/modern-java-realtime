package com.winnguyen1905.talk.model.viewmodel;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class UserContactVm {
  private UUID id;
  private UUID userId;
  private UUID contactId;
  private String firstName;
  private String lastName;
}
