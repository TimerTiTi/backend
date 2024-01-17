package com.titi.pusher.adapter.out.persistence;

import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.titi.pusher.adapter.out.persistence.mapper.NotificationMapper;
import com.titi.pusher.data.jpa.entity.NotificationEntity;
import com.titi.pusher.data.jpa.entity.NotificationHistoryEntity;
import com.titi.pusher.data.jpa.repository.NotificationEntityRepository;
import com.titi.pusher.data.jpa.repository.NotificationHistoryEntityRepository;
import com.titi.pusher.domain.notification.Notification;

@ExtendWith(MockitoExtension.class)
class NotificationPortAdapterTest {

	@Mock
	private NotificationMapper notificationMapper;

	@Mock
	private NotificationEntityRepository notificationEntityRepository;

	@Mock
	private NotificationHistoryEntityRepository notificationHistoryEntityRepository;

	@InjectMocks
	private NotificationPortAdapter notificationPortAdapter;

	@Test
	void createTest() {
		// given
		final Notification notification = mock(Notification.class);
		final NotificationEntity notificationEntity = mock(NotificationEntity.class);
		final NotificationHistoryEntity historyEntity = mock(NotificationHistoryEntity.class);

		given(notificationMapper.toEntity(notification)).willReturn(notificationEntity);
		given(notificationMapper.toHistoryEntity(notification)).willReturn(historyEntity);

		// when
		notificationPortAdapter.create(notification);

		// then
		verify(notificationEntityRepository).save(notificationEntity);
		verify(notificationHistoryEntityRepository).save(historyEntity);
	}

	@Test
	void updateTest() {
		// given
		final Notification notification = mock(Notification.class);
		final NotificationEntity notificationEntity = mock(NotificationEntity.class);
		final NotificationHistoryEntity historyEntity = mock(NotificationHistoryEntity.class);

		given(notificationMapper.toEntity(notification)).willReturn(notificationEntity);
		given(notificationMapper.toHistoryEntity(notification)).willReturn(historyEntity);

		// when
		notificationPortAdapter.update(notification);

		// then
		verify(notificationEntityRepository).save(notificationEntity);
		verify(notificationHistoryEntityRepository).save(historyEntity);
	}

}
