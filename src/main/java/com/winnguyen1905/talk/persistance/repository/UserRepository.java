package com.winnguyen1905.talk.persistance.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.winnguyen1905.talk.persistance.entity.User;

public interface UserRepository extends ReactiveCrudRepository<User, UUID> {
}
