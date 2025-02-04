package com.winnguyen1905.talk.rest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.common.constant.ConversationType;
import com.winnguyen1905.talk.common.constant.ParticipantType;
import com.winnguyen1905.talk.model.dto.CreateConversationDto;
import com.winnguyen1905.talk.model.viewmodel.ConversationVm;
import com.winnguyen1905.talk.persistance.entity.Conversation;
import com.winnguyen1905.talk.persistance.entity.Participant;
import com.winnguyen1905.talk.persistance.entity.User;
import com.winnguyen1905.talk.persistance.repository.ConversationRepository;
import com.winnguyen1905.talk.persistance.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GroupService {

  private final UserRepository userRepository;
  private final ConversationRepository conversationRepository;

  private final Mono<List<Participant>> getParticipants(
      CreateConversationDto request,
      ConversationType conversationType) {

    return Mono.zip(
        userRepository.findById(request.creatorId()),
        userRepository.findById(request.receiverId()))
        .map(tuple -> createParticipants(tuple.getT1(), tuple.getT2(), conversationType));
  }

  private List<Participant> createParticipants(User creator, User receiver, ConversationType conversationType) {
    Participant participantCreator = Participant.builder()
        .user(creator)
        .type(conversationType == ConversationType.POLYLOGUE ? ParticipantType.LEAD : ParticipantType.MEMBER)
        .build();

    Participant participantMember = Participant.builder()
        .user(receiver)
        .type(ParticipantType.MEMBER)
        .build();

    return List.of(participantCreator, participantMember);
  }

  public final Mono<Void> createItem(
      CreateConversationDto request,
      TAccountRequest account) {

    ConversationType conversationType = request.isGroup()
        ? ConversationType.POLYLOGUE
        : ConversationType.DIALOGUE;

    return getParticipants(request, conversationType)
        .map(participants -> Conversation.builder()
            .creatorId(request.creatorId())
            .channelId(request.channelId())
            .type(conversationType)
            .participants(participants)
            .build())
        .flatMap(this.conversationRepository::save).then();
  }

}
