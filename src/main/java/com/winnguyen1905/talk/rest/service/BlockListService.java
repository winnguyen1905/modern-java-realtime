package com.winnguyen1905.talk.rest.service;

import org.springframework.stereotype.Service;

import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.model.dto.BlockListDto;
import com.winnguyen1905.talk.model.viewmodel.BlockListVm;
import com.winnguyen1905.talk.persistance.entity.EBlockList;
import com.winnguyen1905.talk.persistance.repository.BlockListRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import java.util.UUID;

@Service
public class BlockListService {

  private final BlockListRepository blockListRepository;

  public BlockListService(BlockListRepository blockListRepository) {
    this.blockListRepository = blockListRepository;
  }

  // 1. Block a user
  public Mono<BlockListVm> blockUser(BlockListDto blockListDTO, TAccountRequest accountRequest) {
    return blockListRepository.findByBlockerIdAndBlockedId(blockListDTO.getBlockerId(), blockListDTO.getBlockedId())
        .switchIfEmpty(Mono.defer(() -> {
          EBlockList blockEntry = EBlockList.builder()
              .id(UUID.randomUUID())
              .blockerId(blockListDTO.getBlockerId())
              .blockedId(blockListDTO.getBlockedId())
              .build();
          return blockListRepository.save(blockEntry);
        }))
        .map(blockList -> BlockListVm.builder()
            .id(blockList.getId())
            .blockerId(blockList.getBlockerId())
            .blockedId(blockList.getBlockedId())
            .build());
  }

  // 2. Unblock a user
  public Mono<Void> unblockUser(BlockListDto blockListDTO, TAccountRequest accountRequest) {
    return blockListRepository.deleteByBlockerIdAndBlockedId(blockListDTO.getBlockerId(), blockListDTO.getBlockedId());
  }

  // 3. Check if a user is blocked
  public Mono<Boolean> isUserBlocked(BlockListDto blockListDTO, TAccountRequest accountRequest) {
    return blockListRepository.findByBlockerIdAndBlockedId(blockListDTO.getBlockerId(), blockListDTO.getBlockedId())
        .hasElement();
  }

  // 4. Get a list of blocked users
  public Flux<BlockListVm> getBlockedUsers(TAccountRequest accountRequest) {
    return blockListRepository.findByBlockerId(accountRequest.id())
        .map(blockList -> BlockListVm.builder()
            .id(blockList.getId())
            .blockerId(blockList.getBlockerId())
            .blockedId(blockList.getBlockedId())
            .build());
  }
}
