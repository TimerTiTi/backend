package com.titi.titi_pusher.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.titi.titi_pusher.application.port.in.email.SendMailUseCase;
import com.titi.titi_pusher.application.port.out.persistence.notification.CreateNotificationPort;
import com.titi.titi_pusher.application.port.out.persistence.notification.UpdateNotificationPort;
import com.titi.titi_pusher.application.port.out.smtp.SendMailPort;
import com.titi.titi_pusher.domain.email.SimpleMailMessage;
import com.titi.titi_pusher.domain.notification.Notification;
import com.titi.titi_pusher.domain.notification.NotificationCategory;
import com.titi.titi_pusher.domain.notification.NotificationStatus;
import com.titi.titi_pusher.domain.notification.ServiceInfo;

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

	private SendMailUseCase.Command.ToSimpleMessage createSampleToSimpleMessage() {
		return SendMailUseCase.Command.ToSimpleMessage.builder()
			.to("to@to.com")
			.from("from@from.com")
			.subject("subject")
			.text("text")
			.serviceName(ServiceInfo.ServiceName.AUTH.name())
			.serviceRequestId("serviceRequestId")
			.notificationCategory(NotificationCategory.AUTHENTICATION.name())
			.build();
	}

	@Test
	void invokeWithToSimpleMessageShouldCreateNotificationAndSendMail() {
		// given
		final SendMailUseCase.Command.ToSimpleMessage message = createSampleToSimpleMessage();
		given(sendMailPort.sendSimpleMessage(any(SimpleMailMessage.class))).willReturn(true);

		// when
		final SendMailUseCase.Result result = sendMailService.invoke(message);

		// then
		assertThat(result).isNotNull();
		assertThat(result.messageId()).isNotNull();
		assertThat(result.messageStatus()).isEqualTo(NotificationStatus.COMPLETED.name());
		verify(createNotificationPort, times(1)).create(any(Notification.class));
		verify(sendMailPort, times(1)).sendSimpleMessage(any(SimpleMailMessage.class));
		verify(updateNotificationPort, times(1)).update(any(Notification.class));
	}

}
