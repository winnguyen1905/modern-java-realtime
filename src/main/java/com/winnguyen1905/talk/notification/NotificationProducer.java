package com.winnguyen1905.talk.notification;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winnguyen1905.talk.model.dto.NotificationDto;
import com.winnguyen1905.talk.notification.config.NotificationProcessingConfiguration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

@RequiredArgsConstructor
@Service
public class NotificationProducer {

  private final Sender sender;
  private final ObjectMapper objectMapper;

  public Mono<Boolean> send(NotificationDto notify) {
    try {
      byte[] messageBytes = objectMapper.writeValueAsBytes(notify);
      return sender.send(Mono.just(
          new OutboundMessage(
              NotificationProcessingConfiguration.NOTI_DIRECT_EXCHANGE,
              NotificationProcessingConfiguration.NOTI_ROUTING_KEY,
              messageBytes)))
          .doOnSuccess(unused -> System.out.println(">> Sent notification: " + notify))
          .thenReturn(true);
    } catch (JsonProcessingException e) {
      return Mono.error(new RuntimeException("Serialization error", e));
    }
  }
}
