package com.titi.titi_auth.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.titi.exception.TiTiException;
import com.titi.titi_auth.application.port.in.GenerateAuthCodeUseCase;
import com.titi.titi_auth.application.port.out.PutAuthCodePort;
import com.titi.titi_auth.application.port.out.SendAuthCodePort;
import com.titi.titi_auth.domain.AuthCode;
import com.titi.titi_auth.domain.AuthenticationType;

@ExtendWith(MockitoExtension.class)
class GenerateAuthCodeServiceTest {

	@Mock
	private PutAuthCodePort putAuthCodePort;

	@Mock
	private SendAuthCodePort sendAuthCodePort;

	@InjectMocks
	private GenerateAuthCodeService generateAuthCodeService;

	private static GenerateAuthCodeUseCase.Command.ToEmail getCommand() {
		return GenerateAuthCodeUseCase.Command.ToEmail.builder()
			.authType(AuthenticationType.SIGN_UP)
			.email("test@example.com")
			.build();
	}

	@Test
	void invokeTestSuccess() {
		// given
		final GenerateAuthCodeUseCase.Command.ToEmail command = getCommand();
		given(putAuthCodePort.put(any())).willReturn("authKey");

		// when
		final String authKey = generateAuthCodeService.invoke(command);

		// then
		assertThat(authKey).isNotNull();
		verify(putAuthCodePort, times(1)).put(any(AuthCode.class));
		verify(sendAuthCodePort, times(1)).send(any(AuthCode.class));
	}

	@Nested
	class invokeTestFailure {
		@Test
		void whenPutAuthCodePortThrowsTiTiException() {
			// given
			final GenerateAuthCodeUseCase.Command.ToEmail command = getCommand();
			given(putAuthCodePort.put(any())).willThrow(TiTiException.class);

			// when
			final ThrowableAssert.ThrowingCallable throwingCallable = () -> generateAuthCodeService.invoke(command);

			// then
			assertThatCode(throwingCallable).isInstanceOf(TiTiException.class);
			verify(putAuthCodePort, times(1)).put(any(AuthCode.class));
		}
		@Test
		void whenSendAuthCodePortThrowsTiTiException() {
			// given
			final GenerateAuthCodeUseCase.Command.ToEmail command = getCommand();
			doThrow(TiTiException.class).when(sendAuthCodePort).send(any());

			// when
			final ThrowableAssert.ThrowingCallable throwingCallable = () -> generateAuthCodeService.invoke(command);

			// then
			assertThatCode(throwingCallable).isInstanceOf(TiTiException.class);
			verify(putAuthCodePort, times(1)).put(any(AuthCode.class));
			verify(sendAuthCodePort, times(1)).send(any(AuthCode.class));
		}
	}

}
