package com.winnguyen1905.talk.notification;

import com.winnguyen1905.talk.notification.config.DLXNotificationProcessingConfiguration;
import com.winnguyen1905.talk.notification.config.NotificationProcessingConfiguration;

import lombok.RequiredArgsConstructor;

import com.rabbitmq.client.Delivery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.rabbitmq.Receiver;

import jakarta.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class NotificationConsumerDLX {

  // private final Receiver receiver;
  // private final NotificationRepository notificationRepository;

  // @PostConstruct
  // public void consume() {
  //   receiver.consumeAutoAck(NotificationProcessingConfiguration.NOTI_QUEUE_NAME)
  //       .map(Delivery::getBody)
  //       .map(String::new)
  //       .flatMap(message -> notificationRepository.save(new Notification(null, message, LocalDateTime.now(), false)))
  //       .doOnNext(saved -> System.out.println(">> Notification saved: " + saved.getMessage()))
  //       .subscribe();
  // }
}
