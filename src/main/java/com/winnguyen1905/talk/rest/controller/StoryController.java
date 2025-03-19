package com.winnguyen1905.talk.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.model.dto.StoryDto;
import com.winnguyen1905.talk.rest.service.StoryService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/stories")
public class StoryController {

  private final StoryService storyService;

  public StoryController(StoryService storyService) {
    this.storyService = storyService;
  }

  // 1. Create a new story
  @PostMapping
  public Mono<ResponseEntity<StoryDto>> createStory(@RequestBody StoryDto storyDTO, @RequestBody TAccountRequest accountRequest) {
    return storyService.createStory(storyDTO, accountRequest)
        .map(ResponseEntity::ok);
  }

  // 2. Get all active stories
  @GetMapping
  public Flux<StoryDto> getActiveStories(@RequestBody TAccountRequest accountRequest) {
    return storyService.getActiveStories(accountRequest);
  }

  // 3. Get all stories of a specific user
  @GetMapping("/user/{userId}")
  public Flux<StoryDto> getUserStories(@PathVariable UUID userId, @RequestBody TAccountRequest accountRequest) {
    return storyService.getUserStories(userId, accountRequest);
  }

  // 4. Delete a story
  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> deleteStory(@PathVariable UUID id, @RequestBody TAccountRequest accountRequest) {
    return storyService.deleteStory(id, accountRequest)
        .then(Mono.just(ResponseEntity.noContent().build()));
  }
}
