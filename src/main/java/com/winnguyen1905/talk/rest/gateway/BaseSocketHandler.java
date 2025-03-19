package com.winnguyen1905.talk.rest.gateway;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.corundumstudio.socketio.AckCallback;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.winnguyen1905.talk.model.dto.NotificationDto;
import com.winnguyen1905.talk.notification.NotificationProducer;

import reactor.core.publisher.Mono;

public abstract class BaseSocketHandler {

  protected SocketIONamespace namespace;
  protected RedisUserService redisUserService;
  protected NotificationProducer notificationProducer;

  protected abstract void registerEvents();

  protected Mono<Boolean> sendMessageWithAck(UUID receiverId, MessageDto data) {
    CompletableFuture<Boolean> ackFuture = new CompletableFuture<>();

    namespace.getRoomOperations(receiverId.toString()).sendEvent("messageComing", data,
        new AckCallback<String>(String.class) {
          @Override
          public void onSuccess(String result) {
            ackFuture.complete(true); // ACK received, message delivered
          }

          @Override
          public void onTimeout() {
            ackFuture.complete(false); // No ACK received, message not delivered
          }
        });

    return Mono.fromFuture(ackFuture)
        .timeout(Duration.ofSeconds(5)) // Timeout if ACK not received in 5 seconds
        .onErrorResume(ex -> {
          System.out.println("⚠️ No ACK received, sending notification via RabbitMQ...");
          NotificationDto notification = new NotificationDto(receiverId, "New message", data.getMessage());
          return notificationProducer.send(notification);
        });
  }

  public void sendNotificationToUser(NotificationDto notification) {
    sendEvent(notification.receiverId(), "notification", notification).subscribe();
  }

  protected Mono<Void> sendEvent(UUID target, String event, Object data) {
    return redisUserService.getUserSocket(target)
        .flatMap(socketId -> {
          SocketIOClient client = namespace.getClient(socketId);
          if (client != null && client.isChannelOpen()) {
            client.sendEvent(event, data);
            return Mono.empty();
          }
          return Mono.empty();
        })
        .switchIfEmpty(Mono.fromRunnable(() -> namespace.getRoomOperations(target.toString()).sendEvent(event, data)))
        .then();
  }

  protected Mono<Boolean> sendCallWithAck(UUID receiverId, CallDto data) {
    CompletableFuture<Boolean> ackFuture = new CompletableFuture<>();

    return redisUserService.isUserOnline(receiverId)
        .flatMap(isOnline -> {
          if (!isOnline) {
            return sendMissedCallNotification(data).thenReturn(false);
          }

          namespace.getRoomOperations(receiverId.toString())
              .sendEvent("calling", data, new AckCallback<String>(String.class) {
                @Override
                public void onSuccess(String result) {
                  ackFuture.complete(true);
                }

                @Override
                public void onTimeout() {
                  ackFuture.complete(false);
                }
              });

          return Mono.fromFuture(ackFuture)
              .timeout(Duration.ofSeconds(5))
              .onErrorResume(ex -> Mono.just(false));
        });
  }

  protected Mono<Void> sendMissedCallNotification(CallDto data) {
    NotificationDto notification = new NotificationDto(
        data.getCallerId(),
        "Missed Call",
        "Missed call from " + data.getRecieverId());
    return notificationProducer.send(notification).then();
  }
}
