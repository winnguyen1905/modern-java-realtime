package com.winnguyen1905.talk.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.winnguyen1905.talk.common.annotation.AccountRequest;
import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.exception.ErrorVm;
import com.winnguyen1905.talk.model.dto.AttachmentDto;
import com.winnguyen1905.talk.model.viewmodel.AttachmentVm;
import com.winnguyen1905.talk.persistance.entity.EAttachment;
import com.winnguyen1905.talk.rest.service.AttachmentService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/attachments")
public class AttachmentController {

  private final AttachmentService attachmentService;

  public AttachmentController(AttachmentService attachmentService) {
    this.attachmentService = attachmentService;
  }

  // 1. Upload an attachment (Accepts DTO, Returns VM)
  @Operation(summary = "Upload an attachment", description = "Uploads an attachment and returns its VM")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = AttachmentVm.class))),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorVm.class))),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  })
  @PostMapping
  public Mono<ResponseEntity<AttachmentVm>> uploadAttachment(
      @RequestBody @Parameter(description = "Attachment DTO to be uploaded") AttachmentDto attachmentDTO, @AccountRequest TAccountRequest accountRequest) {
    return attachmentService.saveAttachment(attachmentDTO, accountRequest)
        .map(ResponseEntity::ok);
  }

  // 2. Get all attachments for a message (Returns VM)
  @Operation(summary = "Get all attachments for a message", description = "Fetches all attachments related to a given message ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = AttachmentVm.class))),
      @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorVm.class))),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  })
  @GetMapping("/message/{messageId}")
  public Flux<AttachmentVm> getAttachmentsByMessageId(
      @PathVariable @Parameter(description = "ID of the message") String messageId, @AccountRequest TAccountRequest accountRequest) {
    return attachmentService.getAttachmentsByMessageId(messageId, accountRequest);
  }

  // 3. Delete an attachment
  @Operation(summary = "Delete an attachment", description = "Deletes an attachment based on the provided attachment ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "No Content", content = @Content(schema = @Schema(implementation = Void.class))),
      @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorVm.class))),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  })
  @DeleteMapping("/{attachmentId}")
  public Mono<ResponseEntity<Void>> deleteAttachment(
      @PathVariable @Parameter(description = "ID of the attachment to delete") String attachmentId, @AccountRequest TAccountRequest accountRequest) {
    return attachmentService.deleteAttachment(attachmentId, accountRequest)
        .then(Mono.just(ResponseEntity.noContent().build()));
  }
}
