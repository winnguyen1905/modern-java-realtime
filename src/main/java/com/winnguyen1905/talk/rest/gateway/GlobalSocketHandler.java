package com.winnguyen1905.talk.rest.gateway;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.winnguyen1905.talk.auth.CustomUserDetails;
import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.persistance.repository.ParticipantRepository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.UUID;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
@Service
@RequiredArgsConstructor
public class GlobalSocketHandler {
  
  private final SocketIOServer server;
  private final RedisUserService redisUserService;
  private final ParticipantRepository participantRepository;

  @PostConstruct
  public void startServer() {
    server.start();
    log.info("Socket.IO Server Started...");
  }

  @PreDestroy
  public void stopServer() {
    server.stop();
    log.info("Socket.IO Server Stopped...");
  }

  @OnConnect
  public void onConnect(SocketIOClient client) {
    ReactiveSecurityContextHolder.getContext()
        .map(ctx -> (CustomUserDetails) ctx.getAuthentication().getPrincipal())
        .map(userdetail -> TAccountRequest.builder()
            .id(userdetail.id())
            .role(userdetail.accountRole())
            .socketClientId(client.getSessionId()).build())
        .flatMap(accountRequest -> handleUserConnection(client, accountRequest))
        .doOnError(error -> {
          log.error("Connection error: {}", error.getMessage());
          client.disconnect();
        })
        .subscribe();
  }

  private Mono<Void> handleUserConnection(SocketIOClient client, TAccountRequest accountRequest) {
    UUID userId = accountRequest.id();
    UUID socketId = client.getSessionId();

    client.set("uid", userId);
    client.set("user", accountRequest);

    return redisUserService.addUser(userId, socketId)
        .flatMapMany(_ -> participantRepository.findAllConversationIdByUserId(userId))
        .doOnNext(conversationId -> {
          client.joinRoom(conversationId.toString());
          log.info("Client {} joined room: {}", socketId, conversationId);
        })
        .then()
        .doOnSuccess(_ -> log.info("Client connected: {} (userId: {}), stored in Redis", socketId, userId));
  }

  @OnDisconnect
  public void onDisconnect(SocketIOClient client) {
    Mono.justOrEmpty(UUID.fromString(client.get("uid")))
        .flatMap(redisUserService::removeUser)
        .doOnSuccess(removed -> log.info("User disconnected: {}", removed))
        .doOnError(error -> log.error("Disconnection error: {}", error.getMessage()))
        .subscribe();
  }
}
