package com.winnguyen1905.talk.rest.service;

import org.springframework.stereotype.Service;

import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.common.constant.FriendInviteStatus;
import com.winnguyen1905.talk.model.viewmodel.FriendInviteVm;
import com.winnguyen1905.talk.persistance.entity.EFriendInvite;
import com.winnguyen1905.talk.persistance.repository.FriendInviteRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Service
public class FriendInviteService {

  private final FriendshipService friendshipService;
  private final FriendInviteRepository friendInviteRepository;

  public FriendInviteService(FriendInviteRepository friendInviteRepository, FriendshipService friendshipService) {
    this.friendInviteRepository = friendInviteRepository;
    this.friendshipService = friendshipService;
  }

  public Mono<Void> sendFriendRequest(UUID receiverId, TAccountRequest accountRequest) {
    EFriendInvite invite = EFriendInvite.builder()
        .id(UUID.randomUUID())
        .senderId(accountRequest.id())
        .receiverId(receiverId)
        .status(FriendInviteStatus.PENDING)
        .build();

    return friendInviteRepository.save(invite).then();
  }

  public Mono<Void> acceptFriendRequest(UUID id, TAccountRequest accountRequest) {
    return friendInviteRepository.findById(id)
        .flatMap(invite -> {
          invite.setStatus(FriendInviteStatus.ACCEPTED);
          return friendInviteRepository.save(invite)
              .then(friendshipService.addFriend(invite.getSenderId(), invite.getReceiverId()))
              .thenReturn(invite);
        }).then();
  }

  public Mono<Void> rejectFriendRequest(UUID id, TAccountRequest accountRequest) {
    return friendInviteRepository.findById(id)
        .flatMap(invite -> {
          invite.setStatus(FriendInviteStatus.REJECTED);
          return friendInviteRepository.save(invite);
        }).then();
  }

  public Mono<Void> cancelFriendRequest(UUID id, TAccountRequest accountRequest) {
    return friendInviteRepository.deleteById(id);
  }

  public Flux<FriendInviteVm> getPendingRequests(TAccountRequest accountRequest) {
    return friendInviteRepository.findAllByReceiverIdAndStatus(accountRequest.id(), FriendInviteStatus.PENDING)
        .map(friendInvite -> FriendInviteVm.builder()
            .id(friendInvite.getId())
            .senderId(friendInvite.getSenderId())
            .receiverId(friendInvite.getReceiverId())
            .status(friendInvite.getStatus())
            .build());
  }
}
