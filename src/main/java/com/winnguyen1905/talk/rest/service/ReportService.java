package com.winnguyen1905.talk.rest.service;

import org.springframework.stereotype.Service;

import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.common.constant.ReportStatus;
import com.winnguyen1905.talk.model.dto.ReportDto;
import com.winnguyen1905.talk.model.dto.UpdateReportDto;
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
  private ReportDto toDTO(EReport report) {
    return ReportDto.builder()
        .id(report.getId())
        // .title(report.getTitle())
        // .description(report.getDescription())
        .status(report.getStatus())
        .createdAt(report.getCreatedAt())
        .build();
  }

  // TODO: FIX LATER (some field with wrong mean or meanless)
  private EReport toEntity(ReportDto dto) {
    return EReport.builder()
        .id(dto.id() != null ? dto.id() : UUID.randomUUID())
        .notes(dto.title())
        // .description(dto.description())
        .status(dto.status() != null ? dto.status() : ReportStatus.PENDING)
        .createdAt(dto.createdAt() != null ? dto.createdAt() : Instant.now())
        .build();
  }

  // 1. Submit a report
  public Mono<ReportDto> submitReport(ReportDto reportDTO, TAccountRequest accountRequest) {
    EReport report = toEntity(reportDTO);
    return reportRepository.save(report).map(this::toDTO);
  }

  // 2. Get all reports
  public Flux<ReportDto> getAllReports(TAccountRequest accountRequest) {
    return reportRepository.findAll().map(this::toDTO);
  }

  // 3. Get a report by ID
  public Mono<ReportDto> getReportById(UUID id, TAccountRequest accountRequest) {
    return reportRepository.findById(id).map(this::toDTO);
  }

  // 4. Update report status
  public Mono<ReportDto> updateReportStatus(UpdateReportDto updateReportDto, TAccountRequest accountRequest) {
    return reportRepository.findById(updateReportDto.id())
        .flatMap(report -> {
          report.setStatus(updateReportDto.reportStatus());
          return reportRepository.save(report);
        })
        .map(this::toDTO);
  }

  // 5. Delete a report
  public Mono<Void> deleteReport(UUID id, TAccountRequest accountRequest) {
    return reportRepository.deleteById(id);
  }
}
