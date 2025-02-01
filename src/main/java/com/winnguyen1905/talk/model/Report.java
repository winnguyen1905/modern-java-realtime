package com.winnguyen1905.talk.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "report")
public class Report {

  @Id
  @GeneratedValue
  @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
  public UUID id;

  @Column(name = "user_id", nullable = false)
  public UUID userId;

  @Column(name = "participant_id", nullable = false)
  public UUID participantId;

  @Column(name = "report_type", nullable = false)
  public String reportType;

  @Column(name = "notes")
  public String notes;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  public ReportStatus status = ReportStatus.PENDING;

  @Column(name = "created_at", nullable = false, updatable = false)
  public LocalDateTime createdAt = LocalDateTime.now();

  @ManyToOne
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  public User user;

  public Report() {
  }

  public Report(UUID userId, UUID participantId, String reportType, String notes, ReportStatus status) {
    this.userId = userId;
    this.participantId = participantId;
    this.reportType = reportType;
    this.notes = notes;
    this.status = status;
    this.createdAt = LocalDateTime.now();
  }
}
