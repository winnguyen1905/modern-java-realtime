package com.winnguyen1905.talk.model.viewmodel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

import com.winnguyen1905.talk.common.constant.FriendInviteStatus;

@Builder
@Schema(description = "Friend Invite View Model representing a friend request")
public record FriendInviteVm(
    @Schema(description = "Unique identifier of the record", example = "550e8400-e29b-41d4-a716-446655440000") UUID id,
    @Schema(description = "Unique identifier of the friend invite", example = "550e8400-e29b-41d4-a716-446655440000") UUID inviteId,

    @Schema(description = "Sender's user ID", example = "123e4567-e89b-12d3-a456-426614174000") UUID senderId,

    @Schema(description = "Receiver's user ID", example = "987e6543-e21b-34c2-b567-123456789abc") UUID receiverId,

    @Schema(description = "Status of the friend invite", example = "PENDING", allowableValues = {
        "PENDING", "ACCEPTED", "DECLINED" }) FriendInviteStatus status,

    @Schema(description = "Timestamp when the invite was created", example = "2025-02-09T14:30:00Z") String createdAt){
}
