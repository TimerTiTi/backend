package com.titi.titi_pusher.data.jpa.entity;

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

import com.titi.infrastructure.persistence.jpa.entity.BaseHistoryEntity;
import com.titi.titi_pusher.domain.notification.NotificationStatus;

@Entity(name = "notification_histories")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationHistoryEntity extends BaseHistoryEntity {

	@Id
	private Long uid;

	@Column(nullable = false, updatable = false)
	private Long notificationId;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false, updatable = false)
	private NotificationStatus notificationStatus;

}
