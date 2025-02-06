package com.winnguyen1905.talk.rest.service;

import org.springframework.stereotype.Service;

import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.common.constant.MessageType;
import com.winnguyen1905.talk.model.dto.AddMessageDto;
import com.winnguyen1905.talk.model.dto.MessageActionDto;
import com.winnguyen1905.talk.persistance.entity.EMessage;
import com.winnguyen1905.talk.persistance.repository.MessageRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MessageService {
  private final MessageRepository messageRepository;

  public Mono<Void> addMessage(AddMessageDto msg, TAccountRequest account) {
    validateAddMessage(msg, account);
    return Mono.just(
        EMessage.builder()
            .message(msg.content())
            .conversationId(msg.conversationId())
            .senderId(msg.senderId())
            .messageType(MessageType.TEXT).build())
        .flatMap(this.messageRepository::save)
        .then();
  }

  public Mono<Void> deleteMessage(MessageActionDto msg, TAccountRequest account) {
    validateDeleteMessage(msg, account);
    return this.messageRepository.deleteById(msg.id()).then();
  }
}
