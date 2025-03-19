package com.winnguyen1905.talk.rest.service;

import org.springframework.stereotype.Service;

import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.model.dto.DeviceDTO;
import com.winnguyen1905.talk.model.viewmodel.DeviceVm;
import com.winnguyen1905.talk.persistance.entity.EDevice;
import com.winnguyen1905.talk.persistance.repository.DeviceRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import java.time.Instant;
import java.util.UUID;

@Service
public class DeviceService {

  private final DeviceRepository deviceRepository;

  public DeviceService(DeviceRepository deviceRepository) {
    this.deviceRepository = deviceRepository;
  }

  // 1. Register a device
  public Mono<DeviceVm> registerDevice(DeviceDTO deviceDTO, TAccountRequest accountRequest) {
    EDevice device = EDevice.builder()
        .id(UUID.randomUUID())
        .userId(deviceDTO.userId())
        .deviceId(deviceDTO.deviceId())
        .deviceToken(deviceDTO.deviceToken())
        .type(deviceDTO.type())
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();

    return deviceRepository.save(device)
        .map(this::toDeviceVM);
  }

  // 2. Get all devices for a user
  public Flux<DeviceVm> getDevicesByUserId(UUID userId, TAccountRequest accountRequest) {
    return deviceRepository.findByUserId(userId)
        .map(this::toDeviceVM);
  }

  // 3. Get a device by ID
  public Mono<DeviceVm> getDeviceById(UUID id, TAccountRequest accountRequest) {
    return deviceRepository.findById(id)
        .map(this::toDeviceVM);
  }

  // 4. Update a device
  public Mono<DeviceVm> updateDevice(DeviceDTO deviceDTO, TAccountRequest accountRequest) {
    return deviceRepository.findById(deviceDTO.deviceId())
        .flatMap(existing -> {
          existing.setDeviceId(deviceDTO.deviceId());
          existing.setDeviceToken(deviceDTO.deviceToken());
          existing.setType(deviceDTO.type());
          existing.setUpdatedAt(Instant.now());
          return deviceRepository.save(existing);
        })
        .map(this::toDeviceVM);
  }

  // 5. Delete a device
  public Mono<Void> deleteDevice(UUID id, TAccountRequest accountRequest) {
    return deviceRepository.deleteById(id);
  }

  // Helper method to convert EDevice to DeviceVM
  private DeviceVm toDeviceVM(EDevice device) {
    return DeviceVm.builder()
        .id(device.getId())
        .userId(device.getUserId())
        .deviceId(device.getDeviceId())
        .deviceToken(device.getDeviceToken())
        .type(device.getType())
        .createdAt(device.getCreatedAt())
        .updatedAt(device.getUpdatedAt())
        .build();
  }
}
