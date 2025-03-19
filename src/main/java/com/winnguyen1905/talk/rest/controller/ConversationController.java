package com.winnguyen1905.talk.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.winnguyen1905.talk.common.annotation.AccountRequest;
import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.exception.ErrorVm;
import com.winnguyen1905.talk.model.dto.ConversationDto;
import com.winnguyen1905.talk.model.dto.ParticipantActionDto;
import com.winnguyen1905.talk.model.viewmodel.ConversationVm;
import com.winnguyen1905.talk.rest.service.ConversationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/conversations")
@Tag(name = "Conversation API", description = "Operations related to Conversation")
public class ConversationController {

  private final ConversationService conversationService;

  public ConversationController(ConversationService conversationService) {
    this.conversationService = conversationService;
  }

  @Operation(summary = "Create a new conversation", description = "Creates a new conversation with participants.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Conversation created successfully", content = @Content(schema = @Schema(implementation = ConversationVm.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  })
  @PostMapping
  public Mono<ResponseEntity<ConversationVm>> createConversation(
      @RequestBody ConversationDto request,
      @AccountRequest TAccountRequest accountRequest) {
    return conversationService.createConversation(request, accountRequest)
        .map(ResponseEntity::ok);
  }

  @Operation(summary = "Get conversation details", description = "Fetch conversation details by conversation ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Conversation found", content = @Content(schema = @Schema(implementation = ConversationVm.class))),
      @ApiResponse(responseCode = "404", description = "Conversation not found", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  })
  @GetMapping("/{conversationId}")
  public Mono<ResponseEntity<ConversationVm>> getConversation(
      @PathVariable UUID conversationId,
      @AccountRequest TAccountRequest accountRequest) {
    return conversationService.getConversationById(conversationId, accountRequest)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  // @Operation(summary = "Update conversation title", description = "Updates the title of an existing conversation.")
  // @ApiResponses(value = {
  //     @ApiResponse(responseCode = "200", description = "Conversation title updated successfully", content = @Content(schema = @Schema(implementation = ConversationResponseDto.class))),
  //     @ApiResponse(responseCode = "404", description = "Conversation not found", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  // })
  // @PutMapping("/{conversationId}/title")
  // public Mono<ResponseEntity<ConversationResponseDto>> updateConversationTitle(
  //     @PathVariable UUID conversationId,
  //     @RequestParam String newTitle,
  //     @AccountRequest TAccountRequest accountRequest) {
  //   return conversationService.updateConversationTitle(conversationId, newTitle, accountRequest)
  //       .map(ResponseEntity::ok)
  //       .defaultIfEmpty(ResponseEntity.notFound().build());
  // }

  @Operation(summary = "Add participant", description = "Adds a new participant to a conversation.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Participant added successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  })
  @PostMapping("/{conversationId}/participants")
  public Mono<ResponseEntity<Void>> addParticipant(
      @RequestBody ParticipantActionDto request,
      @AccountRequest TAccountRequest accountRequest) {
    return conversationService.addParticipant(request, accountRequest)
        .then(Mono.just(ResponseEntity.ok().build()));
  }

  @Operation(summary = "Remove participant", description = "Removes a participant from a conversation.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Participant removed successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  })
  @DeleteMapping("/{conversationId}/participants/{userId}")
  public Mono<ResponseEntity<Void>> removeParticipant(
      @RequestBody ParticipantActionDto request,
      @AccountRequest TAccountRequest accountRequest) {
    return conversationService.removeParticipant(request, accountRequest)
        .then(Mono.just(ResponseEntity.noContent().build()));
  }
}
