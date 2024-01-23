package com.titi.titi_auth.adapter.out.pusher;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.titi.titi_auth.application.common.constant.AuthConstants;
import com.titi.titi_auth.application.port.out.SendAuthCodePort;
import com.titi.titi_auth.domain.AuthCode;
import com.titi.titi_auth.domain.AuthenticationType;
import com.titi.titi_auth.domain.TargetType;
import com.titi.titi_pusher.adapter.in.internal.InternalSendMailGateway;
import com.titi.titi_pusher.adapter.in.internal.InternalSendMailGateway.SendSimpleMessageRequest;
import com.titi.titi_pusher.adapter.in.internal.dto.SendMessageResponse;

@ExtendWith(MockitoExtension.class)
class SendAuthCodePortAdapterTest {

	@Mock
	private InternalSendMailGateway internalSendMailGateway;

	@InjectMocks
	private SendAuthCodePortAdapter sendAuthCodePortAdapter;

	@Test
	void sendTestSuccess() {
		// given
		final AuthCode authCode = new AuthCode(AuthenticationType.SIGN_UP, "123", TargetType.EMAIL, "test@example.com");
		final SendSimpleMessageRequest expectedRequest = SendSimpleMessageRequest.builder()
			.to("test@example.com")
			.subject(AuthMessageTemplates.GENERATE_AUTH_CODE.getSubject())
			.text(String.format(AuthMessageTemplates.GENERATE_AUTH_CODE.getText(), authCode.authCode()))
			.notificationCategory(SendAuthCodePort.NOTIFICATION_CATEGORY)
			.serviceName(AuthConstants.SERVICE_NAME)
			.serviceRequestId("AUTH_CODE_NOTI_dq2enrB2Oxn6IovRYEilwvc/iqyKHaozSy3D00pEiOY=")
			.build();

		given(internalSendMailGateway.sendSimpleMessage(expectedRequest)).willReturn(
			SendMessageResponse.builder()
				.messageStatus(SendAuthCodePort.MESSAGE_STATUS_COMPLETED)
				.messageId("123456789")
				.build()
		);

		// when
		final boolean result = sendAuthCodePortAdapter.send(authCode);

		// then
		assertThat(result).isTrue();
		verify(internalSendMailGateway, times(1)).sendSimpleMessage(eq(expectedRequest));
	}

	@Test
	void sendTestFailure() {
		// given
		final AuthCode authCode = new AuthCode(AuthenticationType.SIGN_UP, "123", TargetType.EMAIL, "test@example.com");
		final SendSimpleMessageRequest expectedRequest = SendSimpleMessageRequest.builder()
			.to("test@example.com")
			.subject(AuthMessageTemplates.GENERATE_AUTH_CODE.getSubject())
			.text(String.format(AuthMessageTemplates.GENERATE_AUTH_CODE.getText(), authCode.authCode()))
			.notificationCategory(SendAuthCodePort.NOTIFICATION_CATEGORY)
			.serviceName(AuthConstants.SERVICE_NAME)
			.serviceRequestId("AUTH_CODE_NOTI_dq2enrB2Oxn6IovRYEilwvc/iqyKHaozSy3D00pEiOY=")
			.build();
		given(internalSendMailGateway.sendSimpleMessage(expectedRequest)).willReturn(
			SendMessageResponse.builder()
				.messageStatus("FAILED")
				.messageId("987654321")
				.build()
		);

		// when
		final boolean result = sendAuthCodePortAdapter.send(authCode);

		// then
		assertThat(result).isFalse();
		verify(internalSendMailGateway, times(1)).sendSimpleMessage(eq(expectedRequest));
	}
}
