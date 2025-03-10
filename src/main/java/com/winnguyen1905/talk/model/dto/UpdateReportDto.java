package com.winnguyen1905.talk.model.dto;

import java.util.UUID;

import com.winnguyen1905.talk.common.constant.ReportStatus;

public record UpdateReportDto(
  UUID id,
  ReportStatus reportStatus
) {}
