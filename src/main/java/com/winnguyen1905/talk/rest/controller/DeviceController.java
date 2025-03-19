package com.winnguyen1905.talk.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.winnguyen1905.talk.common.annotation.AccountRequest;
import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.model.dto.DeviceDTO;
import com.winnguyen1905.talk.model.viewmodel.DeviceVm;
import com.winnguyen1905.talk.persistance.entity.EDevice;
import com.winnguyen1905.talk.rest.service.DeviceService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;

@RestController
@RequestMapping("/devices")
public class DeviceController {

  private final DeviceService deviceService;

  public DeviceController(DeviceService deviceService) {
    this.deviceService = deviceService;
  }

  // 1. Register a new device
  @Operation(summary = "Register a new device", description = "Registers and returns a new device record.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = DeviceVm.class))),
      @ApiResponse(responseCode = "400", description = "Bad Request")
  })
  @PostMapping
  public Mono<ResponseEntity<DeviceVm>> registerDevice(
      @RequestBody DeviceDTO deviceDTO,
      @AccountRequest TAccountRequest accountRequest) {
    return deviceService.registerDevice(deviceDTO, accountRequest)
        .map(ResponseEntity::ok);
  }

  // 2. Get all devices of a user
  @Operation(summary = "Get all devices of a user", description = "Retrieves a list of all devices for a specific user.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DeviceVm.class)))),
      @ApiResponse(responseCode = "500", description = "Internal Server Error")
  })
  @GetMapping("/user/{userId}")
  public Flux<DeviceVm> getDevicesByUserId(@PathVariable UUID userId, @AccountRequest TAccountRequest accountRequest) {
    return deviceService.getDevicesByUserId(userId, accountRequest);
  }

  // 3. Get a device by ID
  @Operation(summary = "Get a device by ID", description = "Retrieves a device by its unique ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = DeviceVm.class))),
      @ApiResponse(responseCode = "404", description = "Not Found")
  })
  @GetMapping("/{id}")
  public Mono<ResponseEntity<DeviceVm>> getDeviceById(@PathVariable UUID id,
      @AccountRequest TAccountRequest accountRequest) {
    return deviceService.getDeviceById(id, accountRequest)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  // 4. Update a device
  @Operation(summary = "Update a device", description = "Updates an existing device by ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Updated", content = @Content(schema = @Schema(implementation = DeviceVm.class))),
      @ApiResponse(responseCode = "404", description = "Not Found")
  })
  @PutMapping("/{id}")
  public Mono<ResponseEntity<DeviceVm>> updateDevice(
      @RequestBody DeviceDTO deviceDTO,
      @AccountRequest TAccountRequest accountRequest) {
    return deviceService.updateDevice(deviceDTO, accountRequest)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  // 5. Delete a device
  @Operation(summary = "Delete a device", description = "Deletes a device by ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "No Content"),
      @ApiResponse(responseCode = "404", description = "Not Found")
  })
  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> deleteDevice(
      @PathVariable UUID id,
      @AccountRequest TAccountRequest accountRequest) {
    return deviceService.deleteDevice(id, accountRequest)
        .then(Mono.just(ResponseEntity.noContent().build()));
  }
}
