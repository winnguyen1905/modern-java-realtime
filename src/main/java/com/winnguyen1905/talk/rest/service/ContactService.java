package com.winnguyen1905.talk.rest.service;

import org.springframework.stereotype.Service;

import com.winnguyen1905.talk.common.annotation.TAccountRequest;
import com.winnguyen1905.talk.model.dto.ContactDto;
import com.winnguyen1905.talk.model.viewmodel.ContactVm;
import com.winnguyen1905.talk.persistance.entity.EContact;
import com.winnguyen1905.talk.persistance.repository.ContactRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import java.time.Instant;
import java.util.UUID;

@Service
public class ContactService {

  private final ContactRepository contactRepository;

  public ContactService(ContactRepository contactRepository) {
    this.contactRepository = contactRepository;
  }

  // 1. Create a contact
  public Mono<ContactVm> createContact(ContactDto contactDTO, TAccountRequest accountRequest) {
    EContact contact = EContact.builder()
        .id(UUID.randomUUID())
        .firstName(contactDTO.firstName())
        .middleName(contactDTO.middleName())
        .lastName(contactDTO.lastName())
        .phone(contactDTO.phone())
        .email(contactDTO.email())
        .createdAt(Instant.now())
        .build();

    return contactRepository.save(contact)
        .map(saved -> ContactVm.builder()
            .id(saved.getId())
            .firstName(saved.getFirstName())
            .middleName(saved.getMiddleName())
            .lastName(saved.getLastName())
            .phone(saved.getPhone())
            .email(saved.getEmail())
            .createdAt(saved.getCreatedAt())
            .build());
  }

  // 2. Get all contacts
  public Flux<ContactVm> getAllContacts(TAccountRequest accountRequest) {
    return contactRepository.findAll()
        .map(contact -> ContactVm.builder()
            .id(contact.getId())
            .firstName(contact.getFirstName())
            .middleName(contact.getMiddleName())
            .lastName(contact.getLastName())
            .phone(contact.getPhone())
            .email(contact.getEmail())
            .createdAt(contact.getCreatedAt())
            .build());
  }

  // 3. Get a contact by ID
  public Mono<ContactVm> getContactById(UUID id, TAccountRequest accountRequest) {
    return contactRepository.findById(id)
        .map(contact -> ContactVm.builder()
            .id(contact.getId())
            .firstName(contact.getFirstName())
            .middleName(contact.getMiddleName())
            .lastName(contact.getLastName())
            .phone(contact.getPhone())
            .email(contact.getEmail())
            .createdAt(contact.getCreatedAt())
            .build());
  }

  // 4. Update a contact
  public Mono<ContactVm> updateContact(ContactDto contactDTO, TAccountRequest accountRequest) {
    return contactRepository.findById(contactDTO.id())
        .flatMap(existing -> {
          existing.setFirstName(contactDTO.firstName());
          existing.setMiddleName(contactDTO.middleName());
          existing.setLastName(contactDTO.lastName());
          existing.setPhone(contactDTO.phone());
          existing.setEmail(contactDTO.email());
          return contactRepository.save(existing);
        })
        .map(updated -> ContactVm.builder()
            .id(updated.getId())
            .firstName(updated.getFirstName())
            .middleName(updated.getMiddleName())
            .lastName(updated.getLastName())
            .phone(updated.getPhone())
            .email(updated.getEmail())
            .createdAt(updated.getCreatedAt())
            .build());
  }

  // 5. Delete a contact
  public Mono<Void> deleteContact(UUID id, TAccountRequest accountRequest) {
    return contactRepository.deleteById(id);
  }
}
