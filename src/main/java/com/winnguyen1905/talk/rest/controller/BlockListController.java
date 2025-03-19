package com.winnguyen1905.talk.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.winnguyen1905.talk.common.annotation.AccountRequest;
import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.exception.ErrorVm;
import com.winnguyen1905.talk.model.dto.BlockListDto;
import com.winnguyen1905.talk.model.viewmodel.BlockListVm;
import com.winnguyen1905.talk.persistance.entity.EBlockList;
import com.winnguyen1905.talk.rest.service.BlockListService;

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
@RequestMapping("/blocklist")
public class BlockListController {

  private final BlockListService blockListService;

  public BlockListController(BlockListService blockListService) {
    this.blockListService = blockListService;
  }

  // 1. Block a user
  @Operation(summary = "Block a user", description = "Blocks a user by providing blocker and blocked IDs")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = BlockListVm.class))),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorVm.class))),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  })
  @PostMapping("/block")
  public Mono<ResponseEntity<BlockListVm>> blockUser(@RequestBody BlockListDto blockListDTO, @AccountRequest TAccountRequest accountRequest) {
    return blockListService.blockUser(blockListDTO, accountRequest)
        .map(ResponseEntity::ok);
  }

  // 2. Unblock a user
  @Operation(summary = "Unblock a user", description = "Unblocks a user by providing blocker and blocked IDs")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "No Content", content = @Content(schema = @Schema(implementation = Void.class))),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorVm.class))),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  })
  @DeleteMapping("/unblock")
  public Mono<ResponseEntity<Void>> unblockUser(@RequestBody BlockListDto blockListDTO, @AccountRequest TAccountRequest accountRequest) {
    return blockListService.unblockUser(blockListDTO, accountRequest)
        .then(Mono.just(ResponseEntity.noContent().build()));
  }

  // 3. Check if a user is blocked
  @Operation(summary = "Check if a user is blocked", description = "Checks if the specified user is blocked by the blocker")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Boolean.class))),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorVm.class))),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  })
  @GetMapping("/is-blocked")
  public Mono<ResponseEntity<Boolean>> isUserBlocked(@RequestBody BlockListDto blockListDto, @AccountRequest TAccountRequest accountRequest) {
    return blockListService.isUserBlocked(blockListDto, accountRequest)
        .map(ResponseEntity::ok);
  }

  // 4. Get blocked users list
  @Operation(summary = "Get blocked users list", description = "Retrieves all users blocked by the given blocker")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BlockListVm.class))),
      @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorVm.class))),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  })
  @GetMapping("/blocked-users")
  public Flux<BlockListVm> getBlockedUsers(@AccountRequest TAccountRequest accountRequest) {
    return blockListService.getBlockedUsers(accountRequest);
  }
}
