package com.winnguyen1905.talk.persistance.entity;

import jakarta.persistence.*;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

import com.winnguyen1905.talk.common.constant.ReportStatus;

@Entity
@SuperBuilder
@Table(name = "report")
public class Report {
  @Id     
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", columnDefinition = "UUID", updatable = false)
  public UUID id;

  @Column(name = "user_id", nullable = false)
  public UUID userId;

  @Column(name = "participant_id", nullable = false)
  public UUID participantId;

  @Column(name = "report_type", nullable = false)
  public String reportType;

  @Column(name = "notes")
  public String notes;

  @Default
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  public ReportStatus status = ReportStatus.PENDING;

  @ManyToOne
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  public User user;
}
