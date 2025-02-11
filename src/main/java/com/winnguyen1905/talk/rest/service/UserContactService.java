package com.winnguyen1905.talk.rest.service;

import org.springframework.stereotype.Service;

import com.winnguyen1905.talk.common.constant.UserContactDTO;
import com.winnguyen1905.talk.persistance.entity.EUserContact;
import com.winnguyen1905.talk.persistance.repository.UserContactRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class UserContactService {

  private final UserContactRepository userContactRepository;

  public UserContactService(UserContactRepository userContactRepository) {
    this.userContactRepository = userContactRepository;
  }

  // Convert Entity to DTO
  private UserContactDTO toDTO(EUserContact contact) {
    return UserContactDTO.builder()
        .id(contact.getId())
        .userId(contact.getUserId())
        .contactId(contact.getContactId())
        .firstName(contact.getFirstName())
        .lastName(contact.getLastName())
        .build();
  }

  // Convert DTO to Entity
  private EUserContact toEntity(UserContactDTO dto) {
    EUserContact contact = new EUserContact();
    contact.setId(dto.id() != null ? dto.id() : UUID.randomUUID());
    contact.setUserId(dto.userId());
    contact.setContactId(dto.contactId());
    contact.setFirstName(dto.firstName());
    contact.setLastName(dto.lastName());
    return contact;
  }

  // 1. Add a new contact
  public Mono<UserContactDTO> addContact(UserContactDTO contactDTO) {
    EUserContact contact = toEntity(contactDTO);
    return userContactRepository.save(contact).map(this::toDTO);
  }

  // 2. Get all contacts of a user
  public Flux<UserContactDTO> getUserContacts(UUID userId) {
    return userContactRepository.findByUserId(userId).map(this::toDTO);
  }

  // 3. Delete a contact by ID
  public Mono<Void> deleteContact(UUID id) {
    return userContactRepository.deleteById(id);
  }
}
