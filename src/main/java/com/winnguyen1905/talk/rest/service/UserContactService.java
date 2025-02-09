package com.winnguyen1905.talk.rest.service;

import org.springframework.stereotype.Service;

import com.winnguyen1905.talk.persistance.entity.EUserContact;
import com.winnguyen1905.talk.persistance.repository.UserContactRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
public class UserContactService {

  private final UserContactRepository userContactRepository;

  public UserContactService(UserContactRepository userContactRepository) {
    this.userContactRepository = userContactRepository;
  }

  // 1. Add a new contact
  public Mono<EUserContact> addContact(EUserContact contact) {
    contact.setId(UUID.randomUUID());
    return userContactRepository.save(contact);
  }

  // 2. Get all contacts of a user
  public Flux<EUserContact> getUserContacts(UUID userId) {
    return userContactRepository.findByUserId(userId);
  }

  // 3. Delete a contact by ID
  public Mono<Void> deleteContact(UUID id) {
    return userContactRepository.deleteById(id);
  }
}
