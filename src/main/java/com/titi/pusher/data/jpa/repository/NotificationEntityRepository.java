package com.titi.pusher.data.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.titi.pusher.data.jpa.entity.NotificationEntity;

public interface NotificationEntityRepository extends JpaRepository<NotificationEntity, Long> {

}
