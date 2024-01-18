package com.titi.titi_pusher.adapter.out.persistence;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.titi.titi_pusher.adapter.out.persistence.mapper.NotificationMapper;
import com.titi.titi_pusher.application.port.out.persistence.notification.CreateNotificationPort;
import com.titi.titi_pusher.application.port.out.persistence.notification.UpdateNotificationPort;
import com.titi.titi_pusher.data.jpa.entity.NotificationEntity;
import com.titi.titi_pusher.data.jpa.entity.NotificationHistoryEntity;
import com.titi.titi_pusher.data.jpa.repository.NotificationEntityRepository;
import com.titi.titi_pusher.data.jpa.repository.NotificationHistoryEntityRepository;
import com.titi.titi_pusher.domain.notification.Notification;

@Component
@RequiredArgsConstructor
class NotificationPortAdapter implements CreateNotificationPort, UpdateNotificationPort {

	private final NotificationMapper notificationMapper;
	private final NotificationEntityRepository notificationEntityRepository;
	private final NotificationHistoryEntityRepository notificationHistoryEntityRepository;

	@Override
	public void create(Notification notification) {
		final NotificationEntity entity = this.notificationMapper.toEntity(notification);
		this.notificationEntityRepository.save(entity);
		final NotificationHistoryEntity historyEntity = this.notificationMapper.toHistoryEntity(notification);
		this.notificationHistoryEntityRepository.save(historyEntity);
	}

	@Override
	public void update(Notification notification) {
		final NotificationEntity entity = this.notificationMapper.toEntity(notification);
		this.notificationEntityRepository.save(entity);
		final NotificationHistoryEntity historyEntity = this.notificationMapper.toHistoryEntity(notification);
		this.notificationHistoryEntityRepository.save(historyEntity);
	}

}
