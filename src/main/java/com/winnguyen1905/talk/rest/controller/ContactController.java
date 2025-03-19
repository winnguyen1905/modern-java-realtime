package com.winnguyen1905.talk.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.winnguyen1905.talk.common.annotation.AccountRequest;
import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.exception.ErrorVm;
import com.winnguyen1905.talk.model.dto.ContactDto;
import com.winnguyen1905.talk.model.viewmodel.ContactVm;
import com.winnguyen1905.talk.persistance.entity.EContact;
import com.winnguyen1905.talk.rest.service.ContactService;

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
@RequestMapping("/contacts")
public class ContactController {

  private final ContactService contactService;

  public ContactController(ContactService contactService) {
    this.contactService = contactService;
  }

  // 1. Create a new contact
  @Operation(summary = "Create a new contact", description = "Creates and returns a new contact record.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = ContactVm.class))),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorVm.class))),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  })
  @PostMapping
  public Mono<ResponseEntity<ContactVm>> createContact(@RequestBody ContactDto contactDTO, @AccountRequest TAccountRequest accountRequest) {
    return contactService.createContact(contactDTO, accountRequest)
        .map(ResponseEntity::ok);
  }

  // 2. Get all contacts
  @Operation(summary = "Get all contacts", description = "Retrieves a list of all contacts.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ContactVm.class)))),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  })
  @GetMapping
  public Flux<ContactVm> getAllContacts(@AccountRequest TAccountRequest accountRequest) {
    return contactService.getAllContacts(accountRequest);
  }

  // 3. Get a contact by ID
  @Operation(summary = "Get a contact by ID", description = "Retrieves a contact by its unique ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ContactVm.class))),
      @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  })
  @GetMapping("/{id}")
  public Mono<ResponseEntity<ContactVm>> getContactById(@PathVariable UUID id, @AccountRequest TAccountRequest accountRequest) {
    return contactService.getContactById(id, accountRequest)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  // 4. Update a contact
  @Operation(summary = "Update a contact", description = "Updates an existing contact by ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Updated", content = @Content(schema = @Schema(implementation = ContactVm.class))),
      @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  })
  @PutMapping("/{id}")
  public Mono<ResponseEntity<ContactVm>> updateContact(@RequestBody ContactDto contactDTO, @AccountRequest TAccountRequest accountRequest) {
    return contactService.updateContact(contactDTO, accountRequest)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  // 5. Delete a contact
  @Operation(summary = "Delete a contact", description = "Deletes a contact by ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "No Content"),
      @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorVm.class)))
  })
  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> deleteContact(@PathVariable UUID id, @AccountRequest TAccountRequest accountRequest) {
    return contactService.deleteContact(id, accountRequest)
        .then(Mono.just(ResponseEntity.noContent().build()));
  }
}
