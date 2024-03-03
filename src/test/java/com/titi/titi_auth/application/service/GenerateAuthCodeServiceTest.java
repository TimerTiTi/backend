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

import com.titi.titi_auth.application.port.in.GenerateAuthCodeUseCase;
import com.titi.titi_auth.application.port.out.cache.PutAuthCodePort;
import com.titi.titi_auth.application.port.out.pusher.SendAuthCodePort;
import com.titi.titi_auth.common.TiTiAuthException;
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

		// when
		final GenerateAuthCodeUseCase.Result result = generateAuthCodeService.invoke(command);

		// then
		assertThat(result).isNotNull();
		assertThat(result.authKey()).isNotNull();
		verify(putAuthCodePort, times(1)).invoke(any());
		verify(sendAuthCodePort, times(1)).invoke(any());
	}

	@Nested
	class invokeTestFailure {

		@Test
		void whenPutAuthCodePortThrowsTiTiAuthException() {
			// given
			final GenerateAuthCodeUseCase.Command.ToEmail command = getCommand();
			doThrow(TiTiAuthException.class).when(putAuthCodePort).invoke(any());

			// when
			final ThrowableAssert.ThrowingCallable throwingCallable = () -> generateAuthCodeService.invoke(command);

			// then
			assertThatCode(throwingCallable).isInstanceOf(TiTiAuthException.class);
			verify(putAuthCodePort, times(1)).invoke(any());
		}

		@Test
		void whenSendAuthCodePortThrowsTiTiAuthException() {
			// given
			final GenerateAuthCodeUseCase.Command.ToEmail command = getCommand();
			doThrow(TiTiAuthException.class).when(sendAuthCodePort).invoke(any());

			// when
			final ThrowableAssert.ThrowingCallable throwingCallable = () -> generateAuthCodeService.invoke(command);

			// then
			assertThatCode(throwingCallable).isInstanceOf(TiTiAuthException.class);
			verify(putAuthCodePort, times(1)).invoke(any());
			verify(sendAuthCodePort, times(1)).invoke(any());
		}

	}

}
