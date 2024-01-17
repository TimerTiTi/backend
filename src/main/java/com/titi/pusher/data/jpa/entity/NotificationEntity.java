package com.titi.pusher.data.jpa.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.titi.infrastructure.persistence.jpa.entity.BaseEntity;
import com.titi.pusher.domain.notification.NotificationCategory;
import com.titi.pusher.domain.notification.NotificationStatus;
import com.titi.pusher.domain.notification.NotificationType;
import com.titi.pusher.domain.notification.ServiceInfo;
import com.titi.pusher.domain.notification.TargetInfo;

@Entity(name = "notifications")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationEntity extends BaseEntity {

	@Id
	private Long uid;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false, updatable = false)
	private NotificationCategory notificationCategory;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false, updatable = false)
	private NotificationType notificationType;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	private NotificationStatus notificationStatus;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false, updatable = false)
	private TargetInfo.TargetType targetType;

	@Column(nullable = false, updatable = false)
	private String targetValue;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false, updatable = false)
	private ServiceInfo.ServiceName serviceName;

	@Column(nullable = false, updatable = false)
	private String serviceRequestId;

	@Column(nullable = false)
	private LocalDateTime notifiedAt;

}
