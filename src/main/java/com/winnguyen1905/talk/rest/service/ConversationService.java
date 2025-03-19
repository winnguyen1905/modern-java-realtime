package com.winnguyen1905.talk.rest.service;

import lombok.RequiredArgsConstructor;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.common.constant.ConversationType;
import com.winnguyen1905.talk.common.constant.ParticipantType;
import com.winnguyen1905.talk.model.dto.ConversationDto;
import com.winnguyen1905.talk.model.dto.ParticipantActionDto;
import com.winnguyen1905.talk.model.viewmodel.ConversationVm;
import com.winnguyen1905.talk.persistance.entity.EConversation;
import com.winnguyen1905.talk.persistance.entity.EParticipant;
import com.winnguyen1905.talk.persistance.entity.EUser;
import com.winnguyen1905.talk.persistance.repository.ConversationRepository;
import com.winnguyen1905.talk.persistance.repository.ParticipantRepository;
import com.winnguyen1905.talk.persistance.repository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConversationService {
  private final UserRepository userRepository;
  private final ParticipantRepository participantRepository;
  private final ConversationRepository conversationRepository;

  // 🔹 Get participants for a conversation
  private Flux<EParticipant> getParticipants(ConversationDto request, ConversationType type) {
    return Flux.zip(
        userRepository.findById(request.creatorId())
            .switchIfEmpty(Mono.error(new BadRequestException("Creator not found"))),
        userRepository.findById(request.receiverId())
            .switchIfEmpty(Mono.error(new BadRequestException("Receiver not found"))))
        .flatMap(tuple -> Flux.fromIterable(createParticipants(tuple.getT1(), tuple.getT2(), type)));
  }

  // 🔹 Create a conversation
  @Transactional
  public Mono<ConversationVm> createConversation(ConversationDto request, TAccountRequest accountRequest) {
    return validateCreateConversationRequest(request, accountRequest)
        .then(Mono.defer(() -> {
          ConversationType type = request.isGroup() ? ConversationType.POLYLOGUE : ConversationType.DIALOGUE;
          return conversationRepository.save(EConversation.builder()
              .creatorId(request.creatorId())
              .channelId(request.channelId())
              .type(type)
              .build())
              .flatMap(conversation -> assignParticipantsToConversation(conversation, request, type))
              .then(conversationRepository.findById(request.channelId()))
              .map(this::mapToDto);
        }));
  }

  public Flux<UUID> getConversationsByUserId(UUID userId) {
    return this.participantRepository.findAllByUserId(userId)
        .map(EParticipant::getConversationId);
  }

  // 🔹 Assign participants to a conversation
  private Mono<Void> assignParticipantsToConversation(EConversation conversation, ConversationDto request,
      ConversationType type) {
    return getParticipants(request, type)
        .map(participant -> {
          participant.setConversationId(conversation.getId());
          return participant;
        })
        .collectList()
        .flatMapMany(participantRepository::saveAll)
        .then();
  }

  // 🔹 Get conversation by ID with validation
  public Mono<ConversationVm> getConversationById(UUID conversationId, TAccountRequest account) {
    return validateConversationAccess(conversationId, account)
        .then(conversationRepository.findById(conversationId)
            .switchIfEmpty(Mono.error(new BadRequestException("Conversation not found")))
            .flatMap(conversation -> participantRepository.findByConversationId(conversationId)
                .collectList()
                .map(participants -> {
                  conversation.setParticipants(participants);
                  return conversation;
                })))
        .map(this::mapToDto);
  }

  // 🔹 Update conversation title
  public Mono<ConversationVm> updateConversationTitle(UUID conversationId, String newTitle, TAccountRequest accountRequest) {
    return conversationRepository.findById(conversationId)
        .switchIfEmpty(Mono.error(new BadRequestException("Conversation not found")))
        .flatMap(conversation -> {
          conversation.setTitle(newTitle);
          return conversationRepository.save(conversation);
        })
        .map(this::mapToDto);
  }

  // 🔹 Join conversation
  public Mono<Void> joinConversation(ParticipantActionDto request, TAccountRequest account) {
    return validateJoinConversationRequest(request, account)
        .then(participantRepository.save(EParticipant.builder()
            .type(ParticipantType.MEMBER)
            .userId(request.userId())
            .conversationId(request.conversationId())
            .build()))
        .then();
  }

  // 🔹 Add participant
  public Mono<Void> addParticipant(ParticipantActionDto request, TAccountRequest account) {
    return validateAddParticipantRequest(request, account)
        .then(participantRepository.save(EParticipant.builder()
            .userId(request.userId())
            .conversationId(request.conversationId())
            .type(ParticipantType.MEMBER)
            .build()))
        .then();
  }

  // 🔹 Remove participant
  public Mono<Void> removeParticipant(ParticipantActionDto request, TAccountRequest account) {
    return validateRemoveParticipantRequest(request, account)
        .then(participantRepository.findById(request.conversationId()))
        .switchIfEmpty(Mono.error(new BadRequestException("Participant not found")))
        .flatMap(participantRepository::delete)
        .then();
  }

  private List<EParticipant> createParticipants(EUser creator, EUser receiver, ConversationType conversationType) {
    EParticipant participantCreator = EParticipant.builder()
        .user(creator)
        .type(conversationType == ConversationType.POLYLOGUE ? ParticipantType.LEAD : ParticipantType.MEMBER)
        .build();

    EParticipant participantMember = EParticipant.builder()
        .user(receiver)
        .type(ParticipantType.MEMBER)
        .build();

    return List.of(participantCreator, participantMember);
  }

  private ConversationVm mapToDto(EConversation conversation) {
    return ConversationVm.builder()
        .id(conversation.getId())
        .title(conversation.getTitle())
        .creatorId(conversation.getCreatorId())
        .channelId(conversation.getChannelId())
        .type(conversation.getType())
        .build();
  }

  // 🔹 Validate Create Conversation Request
  private Mono<Void> validateCreateConversationRequest(ConversationDto request, TAccountRequest account) {
    if (request.creatorId() == null || request.receiverId() == null) {
      return Mono.error(new BadRequestException("Invalid conversation request. Missing required user IDs."));
    }
    if (!request.creatorId().equals(account.id())) {
      return Mono.error(new BadRequestException("You are not authorized to create this conversation."));
    }
    return Mono.empty();
  }

  // 🔹 Validate Conversation Access
  private Mono<Void> validateConversationAccess(UUID conversationId, TAccountRequest account) {
    return participantRepository.findByConversationIdAndUserId(conversationId, account.id())
        .switchIfEmpty(Mono.error(new BadRequestException("Access denied: You are not a participant.")))
        .then();
  }

  // 🔹 Validate Join Conversation Request
  private Mono<Void> validateJoinConversationRequest(ParticipantActionDto request, TAccountRequest account) {
    if (!request.userId().equals(account.id())) {
      return Mono.error(new BadRequestException("You are not authorized to join this conversation."));
    }
    return Mono.empty();
  }

  // 🔹 Validate Add Participant Request
  private Mono<Void> validateAddParticipantRequest(ParticipantActionDto request, TAccountRequest account) {
    return participantRepository.findByConversationIdAndUserId(request.conversationId(), account.id())
        .switchIfEmpty(Mono.error(new BadRequestException("You are not authorized to add participants.")))
        .then();
  }

  // 🔹 Validate Remove Participant Request
  private Mono<Void> validateRemoveParticipantRequest(ParticipantActionDto request, TAccountRequest account) {
    return participantRepository.findByConversationIdAndUserId(request.conversationId(), account.id())
        .switchIfEmpty(Mono.error(new BadRequestException("You are not authorized to remove participants.")))
        .then();
  }
}
