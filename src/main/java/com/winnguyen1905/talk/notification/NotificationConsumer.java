package com.winnguyen1905.talk.notification;

import com.winnguyen1905.talk.model.dto.NotificationDto;
import com.winnguyen1905.talk.notification.config.NotificationProcessingConfiguration;
import com.winnguyen1905.talk.rest.gateway.ChatSocketHandler;

import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Delivery;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import reactor.rabbitmq.Receiver;
import jakarta.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class NotificationConsumer {

  private final Receiver receiver;
  private final ChatSocketHandler chatSocketHandler; // Inject ChatSocketHandler
  private final ObjectMapper objectMapper;

  @PostConstruct
  public void consume() {
    receiver.consumeAutoAck(NotificationProcessingConfiguration.NOTI_QUEUE_NAME)
        .map(Delivery::getBody)
        .flatMap(this::deserializeNotification)
        .doOnNext(notification -> {
          System.out.println(">> Processing notification: " + notification);

          // Send notification via WebSocket
          chatSocketHandler.sendNotificationToUser(notification);
        })
        .onErrorContinue(
            (throwable, message) -> System.out.println(">> Handling failed notification: " + throwable.getMessage()))
        .subscribe();
  }

  private Mono<NotificationDto> deserializeNotification(byte[] data) {
    try {
      NotificationDto notification = objectMapper.readValue(data, NotificationDto.class);
      return Mono.just(notification);
    } catch (Exception e) {
      return Mono.error(new RuntimeException("Deserialization error", e));
    }
  }
}
