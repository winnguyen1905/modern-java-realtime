package com.winnguyen1905.talk.persistance.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.winnguyen1905.talk.common.constant.ReportStatus;

import java.time.Instant;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "report")
public class EReport {

  @Id
  private UUID id;

  @Column("user_id")
  private UUID userId;

  @Column("participant_id")
  private UUID participantId;

  @Column("report_type")
  private String reportType; // create report type

  @Column("notes")
  private String notes;

  @Column("status")
  private ReportStatus status;

  @Column("created_at")
  private Instant createdAt;
} 
