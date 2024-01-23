package com.titi.titi_pusher.domain.notification;

import java.time.LocalDateTime;

import lombok.Builder;

import com.titi.titi_common_lib.util.UidUtils;

@Builder(toBuilder = true)
public record Notification(
	Long notificationId,
	NotificationCategory notificationCategory,
	NotificationType notificationType,
	NotificationStatus notificationStatus,
	TargetInfo targetInfo,
	ServiceInfo serviceInfo,
	LocalDateTime notifiedAt
) {

	public static Notification ofEmail(NotificationCategory notificationCategory, String email, ServiceInfo serviceInfo) {
		return Notification.builder()
			.notificationId(UidUtils.generateUid())
			.notificationCategory(notificationCategory)
			.notificationType(NotificationType.EMAIL)
			.notificationStatus(NotificationStatus.CREATED)
			.targetInfo(new TargetInfo.EmailTargetInfo(email))
			.serviceInfo(serviceInfo)
			.build();
	}

	public Notification complete() {
		return this.toBuilder()
			.notificationStatus(NotificationStatus.COMPLETED)
			.notifiedAt(LocalDateTime.now())
			.build();
	}

	public Notification fail() {
		return this.toBuilder()
			.notificationStatus(NotificationStatus.FAILED)
			.build();
	}

}
