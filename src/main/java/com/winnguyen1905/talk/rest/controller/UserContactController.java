package com.winnguyen1905.talk.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.winnguyen1905.talk.common.constant.UserContactDTO;
import com.winnguyen1905.talk.rest.service.UserContactService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/contacts")
public class UserContactController {

  private final UserContactService userContactService;

  public UserContactController(UserContactService userContactService) {
    this.userContactService = userContactService;
  }

  // 1. Add a new contact
  @PostMapping
  public Mono<ResponseEntity<UserContactDTO>> addContact(@RequestBody UserContactDTO contactDTO) {
    return userContactService.addContact(contactDTO)
        .map(ResponseEntity::ok);
  }

  // 2. Get all contacts for a specific user
  @GetMapping("/user/{userId}")
  public Flux<UserContactDTO> getUserContacts(@PathVariable UUID userId) {
    return userContactService.getUserContacts(userId);
  }

  // 3. Delete a contact
  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> deleteContact(@PathVariable UUID id) {
    return userContactService.deleteContact(id)
        .then(Mono.just(ResponseEntity.noContent().build()));
  }
}
