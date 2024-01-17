package com.titi.pusher.data.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.titi.pusher.data.jpa.entity.NotificationHistoryEntity;

public interface NotificationHistoryEntityRepository extends JpaRepository<NotificationHistoryEntity, Long> {

}