package com.titi.titi_pusher.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.titi.library.titi_common.util.UidUtils;
import com.titi.library.titi_crypto.util.CryptoUtils;
import com.titi.titi_pusher.data.jpa.entity.NotificationEntity;
import com.titi.titi_pusher.data.jpa.entity.NotificationHistoryEntity;
import com.titi.titi_pusher.domain.notification.Notification;
import com.titi.titi_pusher.domain.notification.TargetInfo;

@Component
@RequiredArgsConstructor
public class NotificationMapper {

	private final CryptoUtils cryptoUtils;

	public NotificationEntity toEntity(Notification notification) {
		return NotificationEntity.builder()
			.uid(notification.notificationId())
			.notificationCategory(notification.notificationCategory())
			.notificationType(notification.notificationType())
			.notificationStatus(notification.notificationStatus())
			.targetType(notification.targetInfo().getType())
			.targetValue(getTargetValue(notification.targetInfo()))
			.serviceName(notification.serviceInfo().name())
			.serviceRequestId(notification.serviceInfo().requestId())
			.notifiedAt(notification.notifiedAt())
			.build();
	}

	public NotificationHistoryEntity toHistoryEntity(Notification notification) {
		return NotificationHistoryEntity.builder()
			.uid(UidUtils.generateUid())
			.notificationId(notification.notificationId())
			.notificationStatus(notification.notificationStatus())
			.build();
	}

	private String getTargetValue(TargetInfo targetInfo) {
		return switch (targetInfo) {
			case TargetInfo.EmailTargetInfo emailTargetInfo -> new String(cryptoUtils.encrypt(emailTargetInfo.email().getBytes()));
		};
	}

}
