package com.winnguyen1905.talk.persistance.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.winnguyen1905.talk.common.constant.DeviceType;

import java.time.Instant;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "device")
public class EDevice {
  @Id
  private UUID id;

  @Column("user_id")
  private UUID userId;

  @Column("device_id")
  private UUID deviceId;

  @Column("device_token")
  private String deviceToken;

  @Column("type")
  private DeviceType type;

  @Column("created_at")
  private Instant createdAt;

  @Column("updated_at")
  private Instant updatedAt;
}


