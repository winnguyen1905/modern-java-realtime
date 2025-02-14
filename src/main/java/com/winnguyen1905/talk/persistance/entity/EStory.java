package com.winnguyen1905.talk.persistance.entity;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.winnguyen1905.talk.common.constant.StoryType;

import java.time.Instant;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "story")
public class EStory {
  @Id
  private UUID id;

  @Column("user_id")
  private UUID userId;

  @Column("content")
  private String content;

  @Column("expires_at")
  private Instant expiresAt;

  @Column("story_type")
  private StoryType storyType;
}
