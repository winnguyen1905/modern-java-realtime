package com.winnguyen1905.talk.persistance.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@Table(name = "attachment", schema = "public")
public class EAttachment {
  @Id
  private String id;

  @Column("message_id")
  private String messageId;

  @Column("thumb_url")
  private String thumbUrl;

  @Column("file_url")
  private String fileUrl;

  @Transient
  private EMessage message;
}
