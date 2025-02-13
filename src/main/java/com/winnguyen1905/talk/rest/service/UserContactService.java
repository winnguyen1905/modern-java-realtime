package com.winnguyen1905.talk.rest.service;

import org.springframework.stereotype.Service;

import com.winnguyen1905.talk.common.constant.UserContactDTO;
import com.winnguyen1905.talk.model.viewmodel.UserContactVM;
import com.winnguyen1905.talk.persistance.entity.EUserContact;
import com.winnguyen1905.talk.persistance.repository.UserContactRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import java.util.UUID;

@Service
public class UserContactService {

  private final UserContactRepository userContactRepository;

  public UserContactService(UserContactRepository userContactRepository) {
    this.userContactRepository = userContactRepository;
  }

  // 1. Add a new contact
  public Mono<UserContactVM> addContact(UserContactDTO contactDTO) {
    EUserContact contact = EUserContact.builder()
        .id(UUID.randomUUID())
        .userId(contactDTO.userId())
        .contactId(contactDTO.contactId())
        .firstName(contactDTO.firstName())
        .lastName(contactDTO.lastName())
        .build();

    return userContactRepository.save(contact)
        .map(savedContact -> UserContactVM.builder()
            .id(savedContact.getId())
            .userId(savedContact.getUserId())
            .contactId(savedContact.getContactId())
            .firstName(savedContact.getFirstName())
            .lastName(savedContact.getLastName())
            .build());
  }

  // 2. Get all contacts for a specific user
  public Flux<UserContactVM> getUserContacts(UUID userId) {
    return userContactRepository.findByUserId(userId)
        .map(contact -> UserContactVM.builder()
            .id(contact.getId())
            .userId(contact.getUserId())
            .contactId(contact.getContactId())
            .firstName(contact.getFirstName())
            .lastName(contact.getLastName())
            .build());
  }

  // 3. Delete a contact
  public Mono<Void> deleteContact(UUID id) {
    return userContactRepository.deleteById(id);
  }
}
