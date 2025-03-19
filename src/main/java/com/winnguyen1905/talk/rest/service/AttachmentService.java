package com.winnguyen1905.talk.rest.service;

import org.springframework.stereotype.Service;

import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.model.dto.AttachmentDto;
import com.winnguyen1905.talk.model.viewmodel.AttachmentVm;
import com.winnguyen1905.talk.persistance.entity.EAttachment;
import com.winnguyen1905.talk.persistance.repository.AttachmentRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID; 

@Service
public class AttachmentService {

  private final AttachmentRepository attachmentRepository;

  public AttachmentService(AttachmentRepository attachmentRepository) {
    this.attachmentRepository = attachmentRepository;
  }

  // Convert Entity to VM
  private AttachmentVm toVM(EAttachment attachment) {
    return AttachmentVm.builder()
        .id(attachment.getId())
        .messageId(attachment.getMessageId())
        .thumbUrl(attachment.getThumbUrl())
        .fileUrl(attachment.getFileUrl())
        .build();
  }

  // Convert DTO to Entity
  private EAttachment toEntity(AttachmentDto dto) {
    return EAttachment.builder()
        .id(dto.id() != null ? dto.id() : UUID.randomUUID().toString())
        .messageId(dto.messageId())
        .thumbUrl(dto.thumbUrl())
        .fileUrl(dto.fileUrl())
        .build();
  }

  // 1. Save an attachment (DTO input, VM output)
  public Mono<AttachmentVm> saveAttachment(AttachmentDto attachmentDTO, TAccountRequest accountRequest) {
    EAttachment attachment = toEntity(attachmentDTO);
    return attachmentRepository.save(attachment).map(this::toVM);
  }

  // 2. Get all attachments for a message (VM output)
  public Flux<AttachmentVm> getAttachmentsByMessageId(String messageId, TAccountRequest accountRequest) {
    return attachmentRepository.findByMessageId(messageId).map(this::toVM);
  }

  // 3. Delete an attachment
  public Mono<Void> deleteAttachment(String attachmentId, TAccountRequest accountRequest) {
    return attachmentRepository.deleteById(attachmentId);
  }
}
