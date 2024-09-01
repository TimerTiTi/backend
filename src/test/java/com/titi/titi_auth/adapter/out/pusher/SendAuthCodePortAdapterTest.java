package com.titi.titi_auth.adapter.out.pusher;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.titi.exception.TiTiException;
import com.titi.titi_auth.application.common.constant.AuthConstants;
import com.titi.titi_auth.application.port.out.pusher.SendAuthCodePort;
import com.titi.titi_auth.domain.AuthCode;
import com.titi.titi_auth.domain.AuthenticationType;
import com.titi.titi_auth.domain.TargetType;
import com.titi.titi_pusher.application.port.in.email.SendMailUseCase;

@ExtendWith(MockitoExtension.class)
class SendAuthCodePortAdapterTest {

	@Mock
	private SendMailUseCase sendMailUseCase;

	@InjectMocks
	private SendAuthCodePortAdapter sendAuthCodePortAdapter;

	@Test
	void sendTestSuccess() {
		// given
		final AuthCode authCode = new AuthCode(AuthenticationType.SIGN_UP, "123", TargetType.EMAIL, "test@example.com");
		final SendMailUseCase.Command expectedRequest = SendMailUseCase.Command.ToSimpleMessage.builder()
			.to("test@example.com")
			.subject(AuthMessageTemplates.GENERATE_AUTH_CODE.getSubject())
			.text(String.format(AuthMessageTemplates.GENERATE_AUTH_CODE.getText(), authCode.authCode()))
			.notificationCategory(SendAuthCodePort.NOTIFICATION_CATEGORY)
			.serviceName(AuthConstants.SERVICE_NAME)
			.serviceRequestId("AUTH_CODE_NOTI_dq2enrB2Oxn6IovRYEilwvc/iqyKHaozSy3D00pEiOY=")
			.build();

		given(sendMailUseCase.invoke(expectedRequest)).willReturn(
			SendMailUseCase.Result.builder()
				.messageStatus(SendAuthCodePort.MESSAGE_STATUS_COMPLETED)
				.messageId("123456789")
				.build()
		);

		// when
		sendAuthCodePortAdapter.invoke(authCode);

		// then
		verify(sendMailUseCase, times(1)).invoke(eq(expectedRequest));
	}

	@Test
	void sendTestFailure() {
		// given
		final AuthCode authCode = new AuthCode(AuthenticationType.SIGN_UP, "123", TargetType.EMAIL, "test@example.com");
		final SendMailUseCase.Command expectedRequest = SendMailUseCase.Command.ToSimpleMessage.builder()
			.to("test@example.com")
			.subject(AuthMessageTemplates.GENERATE_AUTH_CODE.getSubject())
			.text(String.format(AuthMessageTemplates.GENERATE_AUTH_CODE.getText(), authCode.authCode()))
			.notificationCategory(SendAuthCodePort.NOTIFICATION_CATEGORY)
			.serviceName(AuthConstants.SERVICE_NAME)
			.serviceRequestId("AUTH_CODE_NOTI_dq2enrB2Oxn6IovRYEilwvc/iqyKHaozSy3D00pEiOY=")
			.build();
		given(sendMailUseCase.invoke(expectedRequest)).willThrow(TiTiException.class);

		// when
		final ThrowableAssert.ThrowingCallable throwingCallable = () -> sendAuthCodePortAdapter.invoke(authCode);

		// then
		assertThatCode(throwingCallable).isInstanceOf(TiTiException.class);
		verify(sendMailUseCase, times(1)).invoke(eq(expectedRequest));
	}

}
