package com.titi.titi_auth.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.titi.titi_auth.application.common.constant.AuthConstants;
import com.titi.titi_auth.application.port.in.ReissueAccessTokenUseCase;
import com.titi.titi_auth.application.port.out.cache.GetRefreshTokenPort;
import com.titi.titi_auth.application.port.out.cache.PutRefreshTokenPort;
import com.titi.titi_auth.common.TiTiAuthException;

@ExtendWith(MockitoExtension.class)
class ReissueAccessTokenServiceTest {

	@Mock
	private GetRefreshTokenPort getRefreshTokenPort;

	@Mock
	private PutRefreshTokenPort putRefreshTokenPort;

	@Mock
	private JwtService jwtService;

	@InjectMocks
	private ReissueAccessTokenService reissueAccessTokenService;

	@Test
	void successfulScenario() {
		// given
		final JwtService.Subject subject = JwtService.Subject.builder()
			.memberId(String.valueOf(1L))
			.deviceId(UUID.randomUUID().toString())
			.build();
		given(jwtService.extractSubject(anyString(), eq(AuthConstants.REFRESH_TOKEN))).willReturn(subject);
		final GetRefreshTokenPort.Result mockResult = mock(GetRefreshTokenPort.Result.class);
		given(mockResult.refreshToken()).willReturn(Optional.of("refreshToken"));
		given(getRefreshTokenPort.invoke(any(GetRefreshTokenPort.Command.class))).willReturn(mockResult);
		given(jwtService.generateAccessToken(eq(subject), any(Instant.class))).willReturn("accessToken");
		given(jwtService.generateRefreshToken(eq(subject), any(Instant.class))).willReturn("refreshToken");

		// when
		final ReissueAccessTokenUseCase.Command command = ReissueAccessTokenUseCase.Command.builder()
			.refreshToken("refreshToken")
			.build();
		final ReissueAccessTokenUseCase.Result result = reissueAccessTokenService.invoke(command);

		// then
		assertThat(result).isNotNull();
		assertThat(result).usingRecursiveComparison().isNotNull();
		verify(jwtService, times(1)).extractSubject(anyString(), eq(AuthConstants.REFRESH_TOKEN));
		verify(getRefreshTokenPort, times(1)).invoke(any(GetRefreshTokenPort.Command.class));
		verify(jwtService, times(1)).generateAccessToken(eq(subject), any(Instant.class));
		verify(jwtService, times(1)).generateRefreshToken(eq(subject), any(Instant.class));
		verify(putRefreshTokenPort, times(1)).invoke(any(PutRefreshTokenPort.Command.class));
	}

	@Test
	void whenCachedRefreshTokenIsInvalidThenValidateRefreshTokenFailureScenario() {
		// given
		final JwtService.Subject subject = JwtService.Subject.builder()
			.memberId(String.valueOf(1L))
			.deviceId(UUID.randomUUID().toString())
			.build();
		given(jwtService.extractSubject(anyString(), eq(AuthConstants.REFRESH_TOKEN))).willReturn(subject);
		final GetRefreshTokenPort.Result mockResult = mock(GetRefreshTokenPort.Result.class);
		given(mockResult.refreshToken()).willReturn(Optional.empty());
		given(getRefreshTokenPort.invoke(any(GetRefreshTokenPort.Command.class))).willReturn(mockResult);

		// when
		final ReissueAccessTokenUseCase.Command command = ReissueAccessTokenUseCase.Command.builder()
			.refreshToken("refreshToken")
			.build();
		final ThrowableAssert.ThrowingCallable throwingCallable = () -> reissueAccessTokenService.invoke(command);

		// then
		assertThatCode(throwingCallable).isInstanceOf(TiTiAuthException.class);
		verify(jwtService, times(1)).extractSubject(anyString(), eq(AuthConstants.REFRESH_TOKEN));
		verify(getRefreshTokenPort, times(1)).invoke(any(GetRefreshTokenPort.Command.class));
		verify(jwtService, never()).generateAccessToken(eq(subject), any(Instant.class));
		verify(jwtService, never()).generateRefreshToken(eq(subject), any(Instant.class));
		verify(putRefreshTokenPort, never()).invoke(any(PutRefreshTokenPort.Command.class));
	}

	@Test
	void whenCachedRefreshTokenIsMisMatchedThenValidateRefreshTokenFailureScenario() {
		// given
		final JwtService.Subject subject = JwtService.Subject.builder()
			.memberId(String.valueOf(1L))
			.deviceId(UUID.randomUUID().toString())
			.build();
		given(jwtService.extractSubject(anyString(), eq(AuthConstants.REFRESH_TOKEN))).willReturn(subject);
		final GetRefreshTokenPort.Result mockResult = mock(GetRefreshTokenPort.Result.class);
		given(mockResult.refreshToken()).willReturn(Optional.of("mismatchedRefreshToken"));
		given(getRefreshTokenPort.invoke(any(GetRefreshTokenPort.Command.class))).willReturn(mockResult);

		// when
		final ReissueAccessTokenUseCase.Command command = ReissueAccessTokenUseCase.Command.builder()
			.refreshToken("refreshToken")
			.build();
		final ThrowableAssert.ThrowingCallable throwingCallable = () -> reissueAccessTokenService.invoke(command);

		// then
		assertThatCode(throwingCallable).isInstanceOf(TiTiAuthException.class);
		verify(jwtService, times(1)).extractSubject(anyString(), eq(AuthConstants.REFRESH_TOKEN));
		verify(getRefreshTokenPort, times(1)).invoke(any(GetRefreshTokenPort.Command.class));
		verify(jwtService, never()).generateAccessToken(eq(subject), any(Instant.class));
		verify(jwtService, never()).generateRefreshToken(eq(subject), any(Instant.class));
		verify(putRefreshTokenPort, never()).invoke(any(PutRefreshTokenPort.Command.class));
	}

}