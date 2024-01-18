package com.titi.titi_pusher.adapter.in.internal;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.titi.titi_pusher.application.port.in.email.SendMailUseCase;
import com.titi.titi_pusher.domain.notification.Notification;
import com.titi.titi_pusher.domain.notification.NotificationStatus;

@ExtendWith(MockitoExtension.class)
class InternalSendMailGatewayTest {

	@Mock
	private SendMailUseCase sendMailUseCase;

	@InjectMocks
	private InternalSendMailGateway internalSendMailGateway;

	private Notification createNotification() {
		return Notification.builder()
			.notificationId(1651363200000999999L)
			.notificationStatus(NotificationStatus.COMPLETED)
			.build();
	}

	@Test
	void sendSimpleMessage_ShouldSendSimpleMessageAndReturnResponse() {
		// given
		final InternalSendMailGateway.SendSimpleMessageRequest request = InternalSendMailGateway.SendSimpleMessageRequest.builder()
			.to("recipient@example.com")
			.subject("Test Subject")
			.text("Test Message")
			.build();
		given(sendMailUseCase.invoke(any())).willReturn(createNotification());

		// when
		final InternalSendMailGateway.SendSimpleMessageResponse response = internalSendMailGateway.sendSimpleMessage(request);

		// then
		assertThat(response).isNotNull();
		assertThat(response.messageId()).isEqualTo("1651363200000999999");
		assertThat(response.messageStatus()).isEqualTo("COMPLETED");
	}

}
