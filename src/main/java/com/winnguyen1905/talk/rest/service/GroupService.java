package com.winnguyen1905.talk.rest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.common.constant.ConversationType;
import com.winnguyen1905.talk.common.constant.ParticipantType;
import com.winnguyen1905.talk.model.CreateConversationRequest;
import com.winnguyen1905.talk.persistance.entity.Conversation;
import com.winnguyen1905.talk.persistance.entity.Participant;
import com.winnguyen1905.talk.persistance.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupService {
  
  // private final List<Participant> getParticipants(CreateConversationRequest request,
  //     ConversationType conversationType) {
  //   User receiver = this.userRepository.findById(request.receiverId());
  //   User creator = this.userRepository.findById(request.creatorId());
  //   Participant a = Participant.builder()
  //       .user(creator)
  //       .type(conversationType.equals(ConversationType.POLYLOGUE) ? ParticipantType.LEAD : ParticipantType.MEMBER)
  //       .build();
  //   Participant b = Participant.builder()
  //       .user(creator)
  //       .type(conversationType.equals(ParticipantType.MEMBER))
  //       .build();
  //   return List.of(a, b);
  // }

  // public final void createItem(
  //     CreateConversationRequest request, 
  //     TAccountRequest account
  // ) {
  //   // validateRequestCreateItemRequest(request);
  //   ConversationType conversationType = request.isGroup() ? ConversationType.POLYLOGUE : ConversationType.DIALOGUE;
  //   Conversation conversation = Conversation.builder()
  //       .creatorId(request.creatorId())
  //       .channelId(request.channelId())
  //       .type(conversationType)
  //       .participants(getParticipants(request, conversationType)).build();
    
  // }
}
