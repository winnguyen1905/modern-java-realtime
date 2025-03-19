package com.winnguyen1905.talk.rest.service;

import org.springframework.stereotype.Service;

import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.model.dto.AccessDto;
import com.winnguyen1905.talk.model.viewmodel.AccessVm;
import com.winnguyen1905.talk.persistance.entity.EAccess;
import com.winnguyen1905.talk.persistance.repository.AccessRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import java.util.UUID;

@Service
public class AccessService {

  private final AccessRepository accessRepository;

  public AccessService(AccessRepository accessRepository) {
    this.accessRepository = accessRepository;
  }

  // Create or update access record
  public Mono<AccessVm> createOrUpdateAccess(AccessDto accessDto, TAccountRequest accountRequest) {
    return accessRepository.findByUserIdAndDeviceId(accessDto.userId(), accessDto.deviceId())
        .flatMap(existingAccess -> {
          existingAccess.setToken(accessDto.token());
          return accessRepository.save(existingAccess);
        })
        .switchIfEmpty(Mono.defer(() -> {
          EAccess newAccess = EAccess.builder()
              .id(UUID.randomUUID())
              .userId(accessDto.userId())
              .deviceId(accessDto.deviceId())
              .token(accessDto.token()) 
              .build();
          return accessRepository.save(newAccess);
        }))
        .map(access -> AccessVm.builder()
            .id(access.getId())
            .userId(access.getUserId())
            .deviceId(access.getDeviceId())
            .token(access.getToken())
            .build());
  }

  // Get all access records for a user
  public Flux<AccessVm> getUserAccessRecords(TAccountRequest accountRequest) {
    return accessRepository.findByUserId(accountRequest.id())
        .map(access -> AccessVm.builder()
            .id(access.getId())
            .userId(access.getUserId())
            .deviceId(access.getDeviceId())
            .token(access.getToken())
            .build());
  }

  // Delete access record
  public Mono<Void> deleteAccess(AccessDto accessDto, TAccountRequest accountRequest) {
    return accessRepository.deleteByUserIdAndDeviceId(accessDto.userId(), accessDto.deviceId());
  }
}
