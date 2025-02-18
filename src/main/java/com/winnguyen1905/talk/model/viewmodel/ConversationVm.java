package com.winnguyen1905.talk.model.viewmodel;

import java.util.UUID;

import com.winnguyen1905.talk.common.constant.ConversationType;

import lombok.Builder;

import lombok.Builder;
import java.util.UUID;

@Builder
public record ConversationVm(
    UUID id,
    String title,
    UUID creatorId,
    UUID channelId,
    ConversationType type) {
}
