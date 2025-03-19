package com.winnguyen1905.talk.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.winnguyen1905.talk.common.annotation.AccountRequest;
import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.exception.ErrorVm;
import com.winnguyen1905.talk.model.dto.AccessDto;
import com.winnguyen1905.talk.model.viewmodel.AccessVm;
import com.winnguyen1905.talk.persistance.entity.EAccess;
import com.winnguyen1905.talk.rest.service.AccessService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
 
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/access")
@Tag(name = "Access API", description = "APIs for managing user access records")
public class AccessController {

  private final AccessService accessService;

  public AccessController(AccessService accessService) {
    this.accessService = accessService;
  }

  @Operation(summary = "Create or update an access record", description = "Handles login and refresh token update")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = AccessVm.class))),
      @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorVm.class))),
      @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  })
  @PostMapping
  public Mono<ResponseEntity<AccessVm>> createOrUpdateAccess(
      @RequestBody AccessDto accessDto, @AccountRequest TAccountRequest accountRequest) {
    return accessService.createOrUpdateAccess(accessDto, accountRequest)
        .map(ResponseEntity::ok);
  }

  @Operation(summary = "Get all access records for a user", description = "Fetches all access records associated with a user ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = AccessVm.class))),
      @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  })
  @GetMapping("/{userId}")
  public Flux<AccessVm> getUserAccessRecords(@AccountRequest TAccountRequest accountRequest) {
    return accessService.getUserAccessRecords(accountRequest);
  }

  @Operation(summary = "Delete an access record", description = "Handles user logout by removing an access record")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "No Content"),
      @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorVm.class))),
      @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  })
  @DeleteMapping
  public Mono<ResponseEntity<Void>> deleteAccess(
      @RequestBody AccessDto accessDto, @AccountRequest TAccountRequest accountRequest) {
    return accessService.deleteAccess(accessDto, accountRequest)
        .then(Mono.just(ResponseEntity.noContent().build()));
  }
}
