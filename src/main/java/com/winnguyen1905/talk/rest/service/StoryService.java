package com.winnguyen1905.talk.rest.service;

import org.springframework.stereotype.Service;

import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.model.dto.StoryDto;
import com.winnguyen1905.talk.persistance.entity.EStory;
import com.winnguyen1905.talk.persistance.repository.StoryRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Service
public class StoryService {

  private final StoryRepository storyRepository;

  public StoryService(StoryRepository storyRepository) {
    this.storyRepository = storyRepository;
  }

  // Convert Entity to DTO
  private StoryDto toDTO(EStory story) {
    return StoryDto.builder()
        .id(story.getId())
        .userId(story.getUserId())
        .content(story.getContent())
        .expiresAt(story.getExpiresAt())
        .build();
  }

  // Convert DTO to Entity
  private EStory toEntity(StoryDto dto) {
    EStory story = new EStory();
    story.setId(dto.id() != null ? dto.id() : UUID.randomUUID());
    story.setUserId(dto.userId());
    story.setContent(dto.content());
    story.setExpiresAt(dto.expiresAt() != null ? dto.expiresAt() : Instant.now().plusSeconds(24 * 60 * 60));
    return story;
  }

  // 1. Create a new story
  public Mono<StoryDto> createStory(StoryDto storyDTO, TAccountRequest accountRequest) {
    EStory story = toEntity(storyDTO);
    return storyRepository.save(story).map(this::toDTO);
  }

  // 2. Get all active stories (not expired)
  public Flux<StoryDto> getActiveStories(TAccountRequest accountRequest) {
    return storyRepository.findByExpiresAtAfter(Instant.now()).map(this::toDTO);
  }

  // 3. Get stories for a specific user
  public Flux<StoryDto> getUserStories(UUID userId, TAccountRequest accountRequest) {
    return storyRepository.findByUserId(userId).map(this::toDTO);
  }

  // 4. Delete a story by ID
  public Mono<Void> deleteStory(UUID id, TAccountRequest accountRequest) {
    return storyRepository.deleteById(id);
  }
}
