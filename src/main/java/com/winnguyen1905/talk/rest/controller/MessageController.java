package com.winnguyen1905.talk.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.model.dto.EditMessageDto;
import com.winnguyen1905.talk.model.viewmodel.MessageVm;
import com.winnguyen1905.talk.rest.gateway.MessageDto;
import com.winnguyen1905.talk.rest.service.MessageService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@RestController
@RequestMapping("/messages")
public class MessageController {

  private final MessageService messageService;

  public MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  // 1. Send a message
  @PostMapping
  public Mono<ResponseEntity<MessageVm>> sendMessage(@RequestBody MessageDto request, @RequestBody TAccountRequest accountRequest) {
    return messageService.saveMessage(request, accountRequest)
        .map(ResponseEntity::ok);
  }

  // 2. Get messages in a conversation
  @GetMapping("/conversation/{conversationId}")
  public Flux<MessageVm> getMessagesByConversation(@PathVariable UUID conversationId, @RequestBody TAccountRequest accountRequest) {
    return messageService.getMessagesByConversation(conversationId, accountRequest);
  }

  // 3. Get a message by ID
  @GetMapping("/{id}")
  public Mono<ResponseEntity<MessageVm>> getMessageById(@PathVariable UUID id, @RequestBody TAccountRequest accountRequest) {
    return messageService.getMessageById(id, accountRequest)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  // 4. Edit a message
  @PutMapping("/{id}")
  public Mono<ResponseEntity<MessageVm>> editMessage(@RequestBody EditMessageDto editMessageDto, @RequestBody TAccountRequest accountRequest) {
    return messageService.editMessage(editMessageDto, accountRequest)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  // 5. Delete a message
  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> deleteMessage(@PathVariable UUID id, @RequestBody TAccountRequest accountRequest) {
    return messageService.deleteMessage(id, accountRequest)
        .thenReturn(ResponseEntity.noContent().build());
  }
}
