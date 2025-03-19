package com.winnguyen1905.talk.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.winnguyen1905.talk.common.annotation.AccountRequest;
import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.model.viewmodel.FriendVm;
import com.winnguyen1905.talk.rest.service.FriendshipService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@RestController
@RequestMapping("/friendships")
public class FriendshipController {

  private final FriendshipService friendshipService;

  public FriendshipController(FriendshipService friendshipService) {
    this.friendshipService = friendshipService;
  }

  // 1. Add a friendship (after accepting request)
  @PostMapping("/add/{userId}")
  public Mono<ResponseEntity<Void>> addFriend(@PathVariable UUID userId,
      @AccountRequest TAccountRequest accountRequest) {
    return friendshipService.addFriend(userId, accountRequest)
        .map(ResponseEntity::ok);
  }

  // 2. Get all friends of a user
  @GetMapping("/list")
  public Flux<FriendVm> getAllFriends(@AccountRequest TAccountRequest accountRequest) {
    return friendshipService.getAllFriends(accountRequest);
  }

  // 3. Check if two users are friends
  // @GetMapping("/{user1Id}/are-friends/{user2Id}")
  // public Mono<ResponseEntity<Boolean>> areFriends(@PathVariable UUID user1Id, @PathVariable UUID user2Id,
  //     @AccountRequest TAccountRequest accountRequest) {
  //   return friendshipService.areFriends(user1Id, user2Id, accountRequest)
  //       .map(ResponseEntity::ok);
  // }

  // 4. Remove a friendship (Unfriend)
  @DeleteMapping("/remove/{userId}")
  public Mono<ResponseEntity<Void>> removeFriend(@PathVariable UUID userId,
      @AccountRequest TAccountRequest accountRequest) {
    return friendshipService.removeFriend(userId, accountRequest)
        .then(Mono.just(ResponseEntity.noContent().build()));
  }
}
