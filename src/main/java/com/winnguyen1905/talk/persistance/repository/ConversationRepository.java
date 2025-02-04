package com.winnguyen1905.talk.persistance.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.winnguyen1905.talk.persistance.entity.Conversation;

public interface ConversationRepository extends ReactiveCrudRepository<Conversation, UUID> {

}
