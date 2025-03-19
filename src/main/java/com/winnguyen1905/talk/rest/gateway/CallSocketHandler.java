package com.winnguyen1905.talk.rest.gateway;

import com.corundumstudio.socketio.*;
import org.springframework.stereotype.Service;

import com.winnguyen1905.talk.common.constant.CallStatus;
import com.winnguyen1905.talk.notification.NotificationProducer;

import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;
import java.util.Map;

@Service
public class CallSocketHandler extends BaseSocketHandler {
  private final RedisUserService redisUserService;
  private final Map<UUID, CallState> activeCalls = new ConcurrentHashMap<>();

  public CallSocketHandler(SocketIOServer server, RedisUserService redisUserService,
      NotificationProducer notificationProducer) {
    this.namespace = server.addNamespace("/call");
    this.redisUserService = redisUserService;
    this.notificationProducer = notificationProducer;
    registerEvents();
  }

  @Override
  protected void registerEvents() {
    namespace.addEventListener("calling", CallDto.class, this::onCallUser);
    namespace.addEventListener("accept_call", CallDto.class, this::onAnswerCall);
    namespace.addEventListener("refuse_call", CallDto.class, this::onRefuseCall);
    namespace.addEventListener("give_up_call", CallDto.class, this::onGiveUpCall);
    namespace.addEventListener("close_call", CallDto.class, this::onCloseCall);
  }

  private void onCallUser(SocketIOClient client, CallDto data, AckRequest ackSender) {
    CallDto callData = CallDto.builder()
        .id(UUID.randomUUID())
        .callType(data.callType())
        .signal(data.signal())
        .callerId(data.callerId())
        .recieverId(data.recieverId()).build();

    activeCalls.put(callData.id(), CallState.builder()
        .callerId(data.callerId())
        .receiverId(data.recieverId())
        .status(CallStatus.RINGING)
        .build());

    redisUserService.getUserSocket(data.recieverId())
        .flatMap(receiverId -> sendCallWithAck(receiverId, callData))
        .doOnNext(ackReceived -> {
          if (!ackReceived) {
            activeCalls.remove(callData.id());
          }
        })
        .flatMap(_ -> sendEvent(client.getSessionId(), "user_not_online", new HashMap<>())
            .then(sendMissedCallNotification(data)))
        .subscribe();
  }

  private void onAnswerCall(SocketIOClient client, CallDto data, AckRequest ackSender) {
    UUID callId = data.id();
    if (callId == null) {
      System.err.println("Cannot answer call: callId is null");
      return;
    }

    activeCalls.computeIfPresent(callId, (id, state) -> {
      state.setStatus(CallStatus.ACTIVE);
      return state;
    });

    redisUserService.getUserSocket(data.callerId())
        .flatMap(callerSocketId -> sendEvent(callerSocketId, "callAccepted", data))
        .doOnError(error -> {
          System.err.println("Error sending callAccepted event: " + error.getMessage());
        })
        .subscribe();
  }

  private void onRefuseCall(SocketIOClient client, CallDto data, AckRequest ackSender) {
    redisUserService.getUserSocket(data.callerId())
        .flatMap(callerSocketId -> sendEvent(callerSocketId, "refuse_call", data))
        .doOnError(error -> {
          System.err.println("Error sending refuse_call event: " + error.getMessage());
        })
        .subscribe();
  }

  private void onGiveUpCall(SocketIOClient client, CallDto data, AckRequest ackSender) {
    redisUserService.getUserSocket(data.recieverId())
        .flatMap(receiverSocketId -> sendEvent(receiverSocketId, "give_up_call", data))
        .doOnError(error -> {
          System.err.println("Error sending give_up_call event: " + error.getMessage());
        })
        .subscribe();
  }

  private void onCloseCall(SocketIOClient client, CallDto data, AckRequest ackSender) {
    redisUserService.getUserSocket(data.recieverId())
        .flatMap(receiverSocketId -> sendEvent(receiverSocketId, "close_call", data))
        .doOnError(error -> {
          System.err.println("Error sending close_call event: " + error.getMessage());
        })
        .subscribe();
  }
}
