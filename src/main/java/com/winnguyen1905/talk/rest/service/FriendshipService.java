package com.winnguyen1905.talk.rest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winnguyen1905.talk.common.annotation.AccountRequest;
import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.model.viewmodel.FriendVm;
import com.winnguyen1905.talk.persistance.entity.EFriendship;
import com.winnguyen1905.talk.persistance.repository.FriendshipRepository;
import com.winnguyen1905.talk.persistance.repository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Instant;
import java.util.UUID;

@Service
public class FriendshipService {

  private final UserRepository userRepository;
  private final FriendshipRepository friendshipRepository;

  public FriendshipService(FriendshipRepository friendshipRepository, UserRepository userRepository) {
    this.userRepository = userRepository;
    this.friendshipRepository = friendshipRepository;
  }

  // 1. Add a friendship (called after accepting a friend request)
  public Mono<Void> addFriend(UUID userId, TAccountRequest accountRequest) {
    return friendshipRepository.save(
        EFriendship.builder()
            .user1Id(userId)
            .user2Id(accountRequest.id())
            .build())
        .then();
  }

  // 2. Get all friends of a user
  @Transactional
  public Flux<FriendVm> getAllFriends(TAccountRequest request) {
    return friendshipRepository.findAllByUser1IdOrUser2Id(request.id(), request.id())
        .map(friend -> request.id().equals(friend.getUser1Id()) ? friend.getUser2Id() : friend.getUser1Id())
        .collectList()
        .flatMapMany(userRepository::findAllById)
        .map(user -> FriendVm.builder()
            .username(user.getUsername())
            // .avatarUrl(user.getAvatarUrl())
            // .isOnline(user.isOnline())
            .isBlocked(false)
            .isFriend(true)
            .build());
  }

  // 3. Check if two users are friends
  public Mono<Boolean> areFriends(UUID user1Id, UUID user2Id) {
    return friendshipRepository.findByUser1IdAndUser2Id(user1Id, user2Id)
        .switchIfEmpty(friendshipRepository.findByUser1IdAndUser2Id(user2Id, user1Id))
        .hasElement();
  }

  // 4. Remove a friendship (Unfriend)
  public Mono<Void> removeFriend(UUID userId, TAccountRequest accountRequest) {
    return friendshipRepository.deleteByUser1IdAndUser2Id(userId, accountRequest.id())
        .then(friendshipRepository.deleteByUser1IdAndUser2Id(accountRequest.id(), userId));
  }
}
