package com.winnguyen1905.talk.persistance.repository;

import java.util.UUID;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.winnguyen1905.talk.persistance.entity.ENotification;

public interface NotificationRepository extends R2dbcRepository<ENotification, UUID> {
  
}
