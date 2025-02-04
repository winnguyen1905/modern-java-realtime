package com.winnguyen1905.talk.model.viewmodel;

import java.util.UUID;

import lombok.Builder;

@Builder
public record ConversationVm(
  UUID id,
  String name,
  String lastMessage,
  String lastMessageTimestamp,
  Integer unreadMessagesCount,
  String lastMessageSenderName,
  boolean isGroupConversation,
  boolean isMuted,
  boolean isArchived,
  String avatarUrl,
  boolean isParticipantLead,
  boolean isParticipantMember,
  boolean isParticipantOwner,
  boolean isParticipantAdmin,
  boolean isParticipantModerator,
  boolean isParticipantBanned,
  boolean isParticipantMuted,
  boolean isParticipantArchived,
  boolean isParticipantWhitelisted,
  boolean isParticipantBlacklisted,
  boolean isParticipantInvited,
  boolean isParticipantInvitedByModerator,
  boolean isParticipantInvitedByAdmin,
  boolean isParticipantInvitedByOwner,
  boolean isParticipantInvitedByBanned,
  boolean isParticipantInvitedByMuted,
  boolean isParticipantInvitedByArchived,
  boolean isParticipantInvitedByWhitelisted
) {
  
}
