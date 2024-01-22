package com.titi.titi_auth.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
		given(sendAuthCodePort.send(any(AuthCode.class))).willReturn(true);

		// when
		final boolean result = generateAuthCodeService.invoke(command);

		// then
		assertThat(result).isTrue();
		verify(putAuthCodePort, times(1)).put(any(AuthCode.class));
		verify(sendAuthCodePort, times(1)).send(any(AuthCode.class));
	}

	@Test
	void invokeTestFailure() {
		// given
		final GenerateAuthCodeUseCase.Command.ToEmail command = getCommand();

		// when
		final boolean result = generateAuthCodeService.invoke(command);

		// then
		assertThat(result).isFalse();
		verify(putAuthCodePort, times(1)).put(any(AuthCode.class));
		verify(sendAuthCodePort, times(1)).send(any(AuthCode.class));
	}

}
