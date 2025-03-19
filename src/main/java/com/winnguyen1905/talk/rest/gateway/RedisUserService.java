package com.winnguyen1905.talk.rest.gateway;

import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;

import com.winnguyen1905.talk.common.annotation.TAccountRequest;

import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

@Service
public class RedisUserService {
  
  private static final String ONLINE_USERS_KEY = "online_users";
  private static final Duration SESSION_TIMEOUT = Duration.ofMinutes(5);

  private final ReactiveHashOperations<Object, UUID, UUID> hashOps;
  private final ReactiveRedisTemplate<Object, UUID> redisTemplate;

  public RedisUserService(ReactiveRedisTemplate<Object, UUID> redisTemplate) {
    this.redisTemplate = redisTemplate;
    this.hashOps = redisTemplate.opsForHash();
  }

  // ✅ Add user session (userId -> socketId) with expiration
  public Mono<Boolean> addUser(UUID userId, UUID socketId) {
    return hashOps.put(ONLINE_USERS_KEY, userId, socketId)
        .then(redisTemplate.expire(userId, SESSION_TIMEOUT)); // Ensuring TTL on user key
  }

  // ✅ Remove user session when disconnected
  public Mono<Long> removeUser(UUID userId) {
    return hashOps.remove(ONLINE_USERS_KEY, userId);
  }

  // ✅ Retrieve socket ID for a given user
  public Mono<UUID> getUserSocket(UUID userId) {
    return hashOps.get(ONLINE_USERS_KEY, userId);
  }

  // ✅ Check if user is online
  public Mono<Boolean> isUserOnline(UUID userId) {
    return hashOps.hasKey(ONLINE_USERS_KEY, userId);
  }
}
