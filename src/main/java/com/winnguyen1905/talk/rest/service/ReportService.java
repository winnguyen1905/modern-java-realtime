package com.winnguyen1905.talk.rest.service;

import org.springframework.stereotype.Service;

import com.winnguyen1905.talk.common.constant.ReportStatus;
import com.winnguyen1905.talk.model.dto.ReportDTO;
import com.winnguyen1905.talk.persistance.entity.EReport;
import com.winnguyen1905.talk.persistance.repository.ReportRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Service
public class ReportService {

  private final ReportRepository reportRepository;

  public ReportService(ReportRepository reportRepository) {
    this.reportRepository = reportRepository;
  }

  // Convert Entity to DTO
  private ReportDTO toDTO(EReport report) {
    return ReportDTO.builder()
        .id(report.getId())
        // .title(report.getTitle())
        // .description(report.getDescription())
        .status(report.getStatus())
        .createdAt(report.getCreatedAt())
        .build();
  }

  // TODO: FIX LATER (some field with wrong mean or meanless)
  private EReport toEntity(ReportDTO dto) {
    return EReport.builder()
        .id(dto.id() != null ? dto.id() : UUID.randomUUID())
        .notes(dto.title())
        // .description(dto.description())
        .status(dto.status() != null ? dto.status() : ReportStatus.PENDING)
        .createdAt(dto.createdAt() != null ? dto.createdAt() : Instant.now())
        .build();
  }

  // 1. Submit a report
  public Mono<ReportDTO> submitReport(ReportDTO reportDTO) {
    EReport report = toEntity(reportDTO);
    return reportRepository.save(report).map(this::toDTO);
  }

  // 2. Get all reports
  public Flux<ReportDTO> getAllReports() {
    return reportRepository.findAll().map(this::toDTO);
  }

  // 3. Get a report by ID
  public Mono<ReportDTO> getReportById(UUID id) {
    return reportRepository.findById(id).map(this::toDTO);
  }

  // 4. Update report status
  public Mono<ReportDTO> updateReportStatus(UUID id, ReportStatus status) {
    return reportRepository.findById(id)
        .flatMap(report -> {
          report.setStatus(status);
          return reportRepository.save(report);
        })
        .map(this::toDTO);
  }

  // 5. Delete a report
  public Mono<Void> deleteReport(UUID id) {
    return reportRepository.deleteById(id);
  }
}
