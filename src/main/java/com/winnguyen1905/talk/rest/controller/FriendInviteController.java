package com.winnguyen1905.talk.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.winnguyen1905.talk.common.annotation.AccountRequest;
import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.exception.ErrorVm;
import com.winnguyen1905.talk.model.viewmodel.FriendInviteVm;
import com.winnguyen1905.talk.rest.service.FriendInviteService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/friend-invite")
@Tag(name = "Friend Invite API", description = "Operations related to managing friend requests")
public class FriendInviteController {

  private final FriendInviteService friendInviteService;

  public FriendInviteController(FriendInviteService friendInviteService) {
    this.friendInviteService = friendInviteService;
  }

  // 1. Send Friend Request
  @PostMapping("/send/{receiverId}")
  @Operation(summary = "Send a Friend Request", description = "Sends a friend request to the specified user.")
  @ApiResponse(responseCode = "200", description = "Friend request sent successfully")
  @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  public Mono<ResponseEntity<Void>> sendFriendRequest(
      @PathVariable UUID receiverId,
      @AccountRequest TAccountRequest accountRequest) {
    return friendInviteService.sendFriendRequest(receiverId, accountRequest)
        .map(ResponseEntity::ok);
  }

  // 2. Accept Friend Request
  @PutMapping("/{id}/accept")
  @Operation(summary = "Accept a Friend Request", description = "Accepts a pending friend request.")
  @ApiResponse(responseCode = "200", description = "Friend request accepted successfully")
  @ApiResponse(responseCode = "404", description = "Friend request not found", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  public Mono<ResponseEntity<Void>> acceptFriendRequest(
      @PathVariable UUID id,
      @AccountRequest TAccountRequest accountRequest) {
    return friendInviteService.acceptFriendRequest(id, accountRequest)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  // 3. Reject Friend Request
  @PutMapping("/{id}/reject")
  @Operation(summary = "Reject a Friend Request", description = "Rejects a pending friend request.")
  @ApiResponse(responseCode = "200", description = "Friend request rejected successfully")
  @ApiResponse(responseCode = "404", description = "Friend request not found", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  public Mono<ResponseEntity<Void>> rejectFriendRequest(
      @PathVariable UUID id,
      @AccountRequest TAccountRequest accountRequest) {
    return friendInviteService.rejectFriendRequest(id, accountRequest)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  // 4. Cancel Friend Request
  @DeleteMapping("/{id}")
  @Operation(summary = "Cancel a Sent Friend Request", description = "Cancels a previously sent friend request.")
  @ApiResponse(responseCode = "204", description = "Friend request cancelled successfully")
  @ApiResponse(responseCode = "404", description = "Friend request not found", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  public Mono<ResponseEntity<Void>> cancelFriendRequest(
      @PathVariable UUID id,
      @AccountRequest TAccountRequest accountRequest) {
    return friendInviteService.cancelFriendRequest(id, accountRequest)
        .then(Mono.just(ResponseEntity.noContent().build()));
  }

  // 5. Get Pending Friend Requests
  @GetMapping("/pending/{userId}")
  @Operation(summary = "Get Pending Friend Requests", description = "Retrieves all pending friend requests for the user.")
  @ApiResponse(responseCode = "200", description = "List of pending friend requests")
  public Flux<FriendInviteVm> getPendingRequests(
      @AccountRequest TAccountRequest accountRequest) {
    return friendInviteService.getPendingRequests(accountRequest);
  }
}
