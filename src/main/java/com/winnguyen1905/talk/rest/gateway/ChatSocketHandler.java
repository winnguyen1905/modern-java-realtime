package com.winnguyen1905.talk.rest.gateway;

import com.corundumstudio.socketio.*;
import com.winnguyen1905.talk.rest.service.MessageService;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import org.springframework.stereotype.Service;

import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.common.constant.MessageStatus;
import com.winnguyen1905.talk.notification.NotificationProducer;

import java.util.HashMap;
import java.util.UUID;

@Slf4j
@Service
public class ChatSocketHandler extends BaseSocketHandler {

  private final MessageService messageService;
  private final Sinks.Many<MessageDto> messageSink = Sinks.many().multicast().onBackpressureBuffer();

  public ChatSocketHandler(SocketIOServer socketIOServer, MessageService messageService,
      RedisUserService redisUserService, NotificationProducer notificationProducer) {
    super();
    this.namespace = socketIOServer.addNamespace("/chat");
    this.messageService = messageService;
    this.redisUserService = redisUserService;
    this.notificationProducer = notificationProducer;
  }

  @PostConstruct
  public void init() {
    registerEvents();
  }

  @Override
  protected void registerEvents() {
    namespace.addEventListener("join_group", String.class, this::onJoinGroups);
    namespace.addEventListener("on_message", MessageDto.class, this::onMessageReceived);
    namespace.addEventListener("on_typing", MessageDto.class, this::onUserTyping);
    namespace.addEventListener("delete_message", MessageDto.class, this::onDeleteMessage);
    namespace.addEventListener("read_message", MessageDto.class, this::onMessageRead);
  }

  // Reactive stream of messages for external subscribers
  public Flux<MessageDto> getMessageStream() {
    return messageSink.asFlux();
  }

  private void onMessageReceived(SocketIOClient client, MessageDto data, AckRequest ackSender) {
    data.setStatus(MessageStatus.SENT);
    data.setMessageId(UUID.randomUUID());

    UUID receiverId = data.getReceiverId();
    sendMessageWithAck(receiverId, data) // Using inherited method
        .flatMap(isDelivered -> {
          if (!isDelivered) {
            log.warn("Message not acknowledged for receiver: {}", receiverId);
          }
          return messageService.saveMessage(data, client.get("user"));
        })
        .doOnNext(savedMessage -> {
          messageSink.tryEmitNext(data); // Broadcast reactively
          log.info("Message saved and broadcasted: {}", data.getMessageId());
        })
        .doOnError(error -> log.error("Error processing message: {}", data.getMessageId(), error))
        .subscribe();
  }

  private void onMessageRead(SocketIOClient client, MessageDto data, AckRequest ackSender) {
    sendEvent(data.getReceiverId(), "message_read", data) // Using inherited method
        .then(messageService.updateMessageStatus(data.getMessageId(), MessageStatus.READ))
        .doOnSuccess(_ -> {
          messageSink.tryEmitNext(data); // Update stream with read status
          log.info("Message marked as read: {}", data.getMessageId());
        })
        .doOnError(error -> log.error("Error marking message as read: {}", data.getMessageId(), error))
        .subscribe();
  }

  private void onUserTyping(SocketIOClient client, MessageDto data, AckRequest ackSender) {
    sendEvent(data.getReceiverId(), "on_typing", data) // Using inherited method
        .doOnError(error -> log.error("Error sending typing event to user: {}", data.getReceiverId(), error))
        .subscribe();
  }

  private void onDeleteMessage(SocketIOClient client, MessageDto data, AckRequest ackSender) {
    sendEvent(data.getReceiverId(), "delete_message", new HashMap<>()) // Using inherited method
        .doOnError(error -> log.error("Error sending delete event to user: {}", data.getReceiverId(), error))
        .subscribe();
  }

  private void onJoinGroups(SocketIOClient client, String groupName, AckRequest ackSender) {
    client.joinRoom(groupName);
    log.info("Client {} joined group: {}", client.getSessionId(), groupName);
  }
}
