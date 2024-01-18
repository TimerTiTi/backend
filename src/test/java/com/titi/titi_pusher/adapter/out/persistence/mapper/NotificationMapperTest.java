package com.titi.titi_pusher.adapter.out.persistence.mapper;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.titi.titi_crypto_lib.util.CryptoUtils;
import com.titi.titi_pusher.data.jpa.entity.NotificationEntity;
import com.titi.titi_pusher.data.jpa.entity.NotificationHistoryEntity;
import com.titi.titi_pusher.domain.notification.Notification;
import com.titi.titi_pusher.domain.notification.NotificationCategory;
import com.titi.titi_pusher.domain.notification.ServiceInfo;

@ExtendWith(MockitoExtension.class)
class NotificationMapperTest {

	@Mock
	private CryptoUtils cryptoUtils;

	@InjectMocks
	private NotificationMapper notificationMapper;

	private Notification createNotificationOfEmail() {
		return Notification.ofEmail(
			NotificationCategory.AUTHENTICATION,
			"ksp970306@gmail.com",
			ServiceInfo.builder()
				.requestId("requestId")
				.name(ServiceInfo.ServiceName.AUTH)
				.build()
		);
	}

	@Test
	void toEntityTestShouldMapNotificationToEntity() {
		// given
		final Notification notification = createNotificationOfEmail();
		final String targetValue = "ksp970306@gmail.com";
		given(cryptoUtils.encrypt(any(byte[].class))).willReturn(targetValue.getBytes());

		// when
		final NotificationEntity result = notificationMapper.toEntity(notification);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getUid()).isEqualTo(notification.notificationId());
		assertThat(result.getNotificationCategory()).isEqualTo(notification.notificationCategory());
		assertThat(result.getNotificationType()).isEqualTo(notification.notificationType());
		assertThat(result.getNotificationStatus()).isEqualTo(notification.notificationStatus());
		assertThat(result.getTargetType()).isEqualTo(notification.targetInfo().getType());
		assertThat(result.getTargetValue()).isEqualTo(targetValue);
		assertThat(result.getServiceName()).isEqualTo(notification.serviceInfo().name());
		assertThat(result.getServiceRequestId()).isEqualTo(notification.serviceInfo().requestId());
		assertThat(result.getNotifiedAt()).isEqualTo(notification.notifiedAt());
	}

	@Test
	void toHistoryEntityTestShouldMapNotificationToHistoryEntity() {
		// given
		final Notification notification = createNotificationOfEmail();

		// when
		final NotificationHistoryEntity result = notificationMapper.toHistoryEntity(notification);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getUid()).isNotNull();
		assertThat(result.getNotificationId()).isEqualTo(notification.notificationId());
		assertThat(result.getNotificationStatus()).isEqualTo(notification.notificationStatus());
	}

}
