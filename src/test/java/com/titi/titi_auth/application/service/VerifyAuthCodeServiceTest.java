package com.titi.titi_auth.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.titi.titi_auth.application.port.in.VerifyAuthCodeUseCase;
import com.titi.titi_auth.application.port.out.cache.GetAuthCodePort;
import com.titi.titi_auth.application.port.out.cache.RemoveAuthCodePort;
import com.titi.titi_auth.common.TiTiAuthBusinessCodes;
import com.titi.titi_auth.common.TiTiAuthException;
import com.titi.titi_common_lib.util.JwtUtils;

@ExtendWith(MockitoExtension.class)
class VerifyAuthCodeServiceTest {

	@Mock
	private GetAuthCodePort getAuthCodePort;

	@Mock
	private RemoveAuthCodePort removeAuthCodePort;

	@Mock
	private JwtUtils jwtUtils;

	@InjectMocks
	private VerifyAuthCodeService verifyAuthCodeService;

	@Test
	void whenSuccessfullyVerifyAuthCodeThenReturnAuthToken() {
		// given
		final String authKey = "authKey";
		final String authCode = "authCode";
		final String authToken = "authToken";
		given(getAuthCodePort.invoke(any())).willReturn(GetAuthCodePort.Result.builder().authCode(Optional.of(authCode)).build());
		given(jwtUtils.generate(any())).willReturn(authToken);

		// when
		final VerifyAuthCodeUseCase.Result result = verifyAuthCodeService.invoke(
			VerifyAuthCodeUseCase.Command.builder()
				.authKey(authKey)
				.authCode(authCode)
				.build()
		);

		// then
		assertThat(result).isNotNull();
		assertThat(result.authToken()).isNotNull();
		assertThat(result.authToken()).isEqualTo(authToken);
		verify(getAuthCodePort, times(1)).invoke(any());
		verify(removeAuthCodePort, times(1)).invoke(any());
		verify(jwtUtils, times(1)).generate(any());
	}

	@Test
	void whenAuthCodeIsNotInCacheThenThrowsTiTiAuthException() {
		// given
		final String authKey = "authKey";
		final String authCode = "authCode";
		given(getAuthCodePort.invoke(any())).willReturn(GetAuthCodePort.Result.builder().authCode(Optional.empty()).build());

		// when
		final ThrowableAssert.ThrowingCallable throwingCallable = () -> verifyAuthCodeService.invoke(
			VerifyAuthCodeUseCase.Command.builder()
				.authKey(authKey)
				.authCode(authCode)
				.build()
		);

		// then
		assertThatCode(throwingCallable).isInstanceOf(TiTiAuthException.class)
			.hasMessageContaining(TiTiAuthBusinessCodes.INVALID_AUTH_CODE.getMessage());
		verify(getAuthCodePort, times(1)).invoke(any());
		verify(removeAuthCodePort, never()).invoke(any());
		verify(jwtUtils, never()).generate(any());
	}

	@Test
	void whenAuthCodeIsNotMatchedThenThrowsTiTiAuthException() {
		// given
		final String authKey = "authKey";
		final String cachedAuthCode = "cachedAuthCode";
		final String authCode = "authCode";
		given(getAuthCodePort.invoke(any())).willReturn(GetAuthCodePort.Result.builder().authCode(Optional.of(cachedAuthCode)).build());

		// when
		final ThrowableAssert.ThrowingCallable throwingCallable = () -> verifyAuthCodeService.invoke(
			VerifyAuthCodeUseCase.Command.builder()
				.authKey(authKey)
				.authCode(authCode)
				.build()
		);

		// then
		assertThatCode(throwingCallable).isInstanceOf(TiTiAuthException.class)
			.hasMessageContaining(TiTiAuthBusinessCodes.MISMATCHED_AUTH_CODE.getMessage());
		verify(getAuthCodePort, times(1)).invoke(any());
		verify(removeAuthCodePort, never()).invoke(any());
		verify(jwtUtils, never()).generate(any());
	}

}
