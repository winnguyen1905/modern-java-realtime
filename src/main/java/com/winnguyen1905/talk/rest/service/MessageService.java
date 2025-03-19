package com.winnguyen1905.talk.rest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.common.constant.MessageType;
import com.winnguyen1905.talk.model.dto.EditMessageDto;
import com.winnguyen1905.talk.model.viewmodel.MessageVm;
import com.winnguyen1905.talk.persistance.entity.EMessage;
import com.winnguyen1905.talk.persistance.repository.MessageRepository;
import com.winnguyen1905.talk.rest.gateway.MessageDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;
import java.time.Instant;

@Service
public class MessageService {

  private final MessageRepository messageRepository;

  public MessageService(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  public Mono<Void> updateMessageStatus(UUID messageId, TAccountRequest accountRequest) {
    return this.messageRepository.findByIdAndSenderId(messageId, accountRequest.id())
        .map(message -> {
          message.setIsSeen(true);
          return message;
        })
        .flatMap(messageRepository::save)
        .then();
  }

  // 1. Send a message
  @Transactional
  public Mono<MessageVm> saveMessage(MessageDto request, TAccountRequest accountRequest) {
    EMessage message = EMessage.builder()
        .id(UUID.randomUUID())
        .guid(UUID.randomUUID().toString())
        .conversationId(request.conversationId())
        .senderId(request.senderId())
        .message(request.message())
        .messageType(request.messageType())
        .build();

    return messageRepository.save(message)
        .map(savedMessage -> MessageVm.builder()
            .id(savedMessage.getId())
            .guid(savedMessage.getGuid())
            .receiverId(savedMessage.getConversationId())
            .senderId(savedMessage.getSenderId())
            .message(savedMessage.getMessage())
            .messageType(savedMessage.getMessageType())
            .createdAt(Instant.now())
            .build());
  }

  // 2. Get messages in a conversation
  public Flux<MessageVm> getMessagesByConversation(UUID conversationId, TAccountRequest accountRequest) {
    return messageRepository.findByConversationId(conversationId)
        .map(message -> MessageVm.builder()
            .id(message.getId())
            .guid(message.getGuid())
            .conversationId(message.getConversationId())
            .senderId(message.getSenderId())
            .message(message.getMessage())
            .messageType(message.getMessageType())
            .createdAt(Instant.now())
            .build());
  }

  // 3. Get a message by ID
  public Mono<MessageVm> getMessageById(UUID id, TAccountRequest accountRequest) {
    return messageRepository.findById(id)
        .map(message -> MessageVm.builder()
            .id(message.getId())
            .guid(message.getGuid())
            .conversationId(message.getConversationId())
            .senderId(message.getSenderId())
            .message(message.getMessage())
            .messageType(message.getMessageType())
            .createdAt(Instant.now())
            .build());
  }

  // 4. Edit a message
  @Transactional
  public Mono<MessageVm> editMessage(EditMessageDto editMessageDto, TAccountRequest accountRequest) {
    return messageRepository.findById(editMessageDto.id())
        .filter(msg -> msg.getMessageType() == MessageType.TEXT)
        .flatMap(msg -> {
          msg.setMessage(editMessageDto.message());
          return messageRepository.save(msg);
        })
        .map(message -> MessageVm.builder()
            .id(message.getId())
            .guid(message.getGuid())
            .conversationId(message.getConversationId())
            .senderId(message.getSenderId())
            .message(message.getMessage())
            .messageType(message.getMessageType())
            .createdAt(Instant.now())
            .build());
  }

  // 5. Delete a message
  @Transactional
  public Mono<Void> deleteMessage(UUID id, TAccountRequest accountRequest) {
    return messageRepository.deleteById(id);
  }
}
