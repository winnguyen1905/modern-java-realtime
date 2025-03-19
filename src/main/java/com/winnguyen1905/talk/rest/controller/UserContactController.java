package com.winnguyen1905.talk.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.common.constant.UserContactDTO;
import com.winnguyen1905.talk.exception.ErrorVm;
import com.winnguyen1905.talk.model.viewmodel.UserContactVm;
import com.winnguyen1905.talk.rest.service.UserContactService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import java.util.UUID;

@RestController
@RequestMapping("/contacts")
public class UserContactController {

  private final UserContactService userContactService;

  public UserContactController(UserContactService userContactService) {
    this.userContactService = userContactService;
  }

  // 1. Add a new contact
  @Operation(summary = "Add a new contact", description = "Creates a new user contact entry")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = UserContactVm.class))),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorVm.class))),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  })
  @PostMapping
  public Mono<ResponseEntity<UserContactVm>> addContact(@RequestBody UserContactDTO contactDTO,
      @RequestBody TAccountRequest accountRequest) {
    return userContactService.addContact(contactDTO, accountRequest)
        .map(ResponseEntity::ok);
  }

  // 2. Get all contacts for a specific user
  @Operation(summary = "Get user contacts", description = "Retrieves all contacts associated with a user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = UserContactVm.class))),
      @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorVm.class))),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  })
  @GetMapping("/user/{userId}")
  public Flux<UserContactVm> getUserContacts(@RequestBody TAccountRequest accountRequest) {
    return userContactService.getUserContacts(accountRequest);
  }

  // 3. Delete a contact
  @Operation(summary = "Delete a contact", description = "Removes a contact entry by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "No Content", content = @Content(schema = @Schema(implementation = Void.class))),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorVm.class))),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  })
  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> deleteContact(@PathVariable UUID id, @RequestBody TAccountRequest accountRequest) {
    return userContactService.deleteContact(id, accountRequest)
        .then(Mono.just(ResponseEntity.noContent().build()));
  }
}
