package com.winnguyen1905.talk.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.common.constant.ReportStatus;
import com.winnguyen1905.talk.model.dto.ReportDto;
import com.winnguyen1905.talk.model.dto.UpdateReportDto;
import com.winnguyen1905.talk.rest.service.ReportService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/reports")
public class ReportController {

  private final ReportService reportService;

  public ReportController(ReportService reportService) {
    this.reportService = reportService;
  }

  // 1. Submit a report
  @PostMapping
  public Mono<ResponseEntity<ReportDto>> submitReport(@RequestBody ReportDto reportDTO, @RequestBody TAccountRequest accountRequest) {
    return reportService.submitReport(reportDTO, accountRequest)
        .map(ResponseEntity::ok);
  }

  // 2. Get all reports
  @GetMapping
  public Flux<ReportDto> getAllReports(@RequestBody TAccountRequest accountRequest) {
    return reportService.getAllReports(accountRequest);
  }

  // 3. Get a report by ID
  @GetMapping("/{id}")
  public Mono<ResponseEntity<ReportDto>> getReportById(@PathVariable UUID id, @RequestBody TAccountRequest accountRequest) {
    return reportService.getReportById(id, accountRequest)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  // 4. Update report status
  @PatchMapping("/{id}/status")
  public Mono<ResponseEntity<ReportDto>> updateReportStatus(@RequestBody UpdateReportDto updateReportDto, @RequestBody TAccountRequest accountRequest) {
    return reportService.updateReportStatus(updateReportDto, accountRequest)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  // 5. Delete a report
  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> deleteReport(@PathVariable UUID id, @RequestBody TAccountRequest accountRequest) {
    return reportService.deleteReport(id, accountRequest)
        .then(Mono.just(ResponseEntity.noContent().build()));
  }
}
