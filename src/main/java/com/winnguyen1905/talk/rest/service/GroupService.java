package com.winnguyen1905.talk.rest.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.common.constant.ConversationType;
import com.winnguyen1905.talk.common.constant.ParticipantType;
import com.winnguyen1905.talk.model.dto.CreateConversationDto;
import com.winnguyen1905.talk.model.dto.ParticipantActionDto;
import com.winnguyen1905.talk.model.viewmodel.ConversationVm;
import com.winnguyen1905.talk.persistance.entity.EConversation;
import com.winnguyen1905.talk.persistance.entity.EParticipant;
import com.winnguyen1905.talk.persistance.entity.User;
import com.winnguyen1905.talk.persistance.repository.ConversationRepository;
import com.winnguyen1905.talk.persistance.repository.ParticipantRepository;
import com.winnguyen1905.talk.persistance.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GroupService {
  private final UserRepository userRepository;
  private final ParticipantRepository participantRepository;
  private final ConversationRepository conversationRepository;

  private final Flux<EParticipant> getParticipants(
      CreateConversationDto request,
      ConversationType conversationType) {

    return Mono.zip(
        userRepository.findById(request.creatorId()),
        userRepository.findById(request.receiverId()))
        .flatMapMany(tuple -> Flux.fromIterable(createParticipants(tuple.getT1(), tuple.getT2(), conversationType)));
  }

  private List<EParticipant> createParticipants(User creator, User receiver, ConversationType conversationType) {
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

  @Transactional
  public final Mono<Void> createConversation(
      CreateConversationDto request, TAccountRequest account) {
    validateCreateConversationRequest(request, account);

    ConversationType conversationType = request.isGroup()
        ? ConversationType.POLYLOGUE
        : ConversationType.DIALOGUE;

    return Mono.just(
        EConversation.builder()
            .creatorId(request.creatorId())
            .channelId(request.channelId())
            .type(conversationType)
            .build())
        .flatMap(this.conversationRepository::save)
        .flatMap(conversation -> getParticipants(request, conversationType)
            .map(participant -> {
              participant.setConversationId(conversation.getId());
              return participant;
            })
            .collectList())
        .map(this.participantRepository::saveAll)
        .then();
  }

  public final Mono<Void> joinConversation(ParticipantActionDto joinConversationDto, TAccountRequest account) {
    validateJoinConversationRequest(joinConversationDto, account);
    return Mono.just(
        EParticipant.builder()
            .type(ParticipantType.MEMBER)
            .userId(joinConversationDto.userId())
            .conversationId(joinConversationDto.conversationId()).build())
        .flatMap(this.participantRepository::save).then();
  }

  public final Mono<Void> addParticipant(ParticipantActionDto joinConversationDto, TAccountRequest account) {
    validateAddParticipantRequest(joinConversationDto, account);
    return Mono.just(
        EParticipant.builder()
            .userId(joinConversationDto.userId())
            .conversationId(joinConversationDto.conversationId())
            .type(ParticipantType.MEMBER)
            .build())
        .map(this.participantRepository::save).then();
  }

  public Mono<Void> removeParticipant(ParticipantActionDto removeParticipantDto, TAccountRequest account) {
    validateRemoveParticipantRequest(removeParticipantDto, account);
    return this.participantRepository.findById(removeParticipantDto.conversationId())
        .flatMap(this.participantRepository::delete).then();
  }

}
