package com.winnguyen1905.talk.persistance.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.winnguyen1905.talk.common.constant.FriendInviteStatus;
import com.winnguyen1905.talk.persistance.entity.EFriendInvite;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface FriendInviteRepository extends R2dbcRepository<EFriendInvite, UUID> {
  Mono<EFriendInvite> findBySenderIdAndReceiverId(UUID senderId, UUID receiverId);
  Flux<EFriendInvite> findAllByReceiverIdAndStatus(UUID receiverId, FriendInviteStatus friendInviteStatus);
}
