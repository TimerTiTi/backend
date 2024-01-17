package com.titi.pusher.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.titi.pusher.application.port.in.email.SendMailUseCase;
import com.titi.pusher.application.port.out.persistence.notification.CreateNotificationPort;
import com.titi.pusher.application.port.out.persistence.notification.UpdateNotificationPort;
import com.titi.pusher.application.port.out.smtp.SendMailPort;
import com.titi.pusher.domain.email.SimpleMailMessage;
import com.titi.pusher.domain.notification.Notification;
import com.titi.pusher.domain.notification.NotificationCategory;
import com.titi.pusher.domain.notification.ServiceInfo;

@ExtendWith(MockitoExtension.class)
class SendMailServiceTest {

	@Mock
	private SendMailPort sendMailPort;

	@Mock
	private CreateNotificationPort createNotificationPort;

	@Mock
	private UpdateNotificationPort updateNotificationPort;

	@InjectMocks
	private SendMailService sendMailService;

	@Test
	void invokeWithToSimpleMessageShouldCreateNotificationAndSendMail() {
		// given
		final SendMailUseCase.Command.ToSimpleMessage message = createSampleToSimpleMessage();

		// when
		final Notification notification = sendMailService.invoke(message);

		// then
		assertThat(notification).isNotNull();
		verify(createNotificationPort, times(1)).create(any(Notification.class));
		verify(sendMailPort, times(1)).sendSimpleMessage(any(SimpleMailMessage.class));
		verify(updateNotificationPort, times(1)).update(any(Notification.class));
	}

	private SendMailUseCase.Command.ToSimpleMessage createSampleToSimpleMessage() {
		return SendMailUseCase.Command.ToSimpleMessage.builder()
			.to("to@to.com")
			.from("from@from.com")
			.subject("subject")
			.text("text")
			.serviceName(ServiceInfo.ServiceName.AUTH)
			.serviceRequestId("serviceRequestId")
			.notificationCategory(NotificationCategory.AUTHENTICATION)
			.build();
	}

}
