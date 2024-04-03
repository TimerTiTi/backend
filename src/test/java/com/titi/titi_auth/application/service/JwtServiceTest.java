package com.titi.titi_auth.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.Instant;
import java.util.UUID;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.jsonwebtoken.Claims;

import com.titi.titi_auth.application.common.constant.AuthConstants;
import com.titi.titi_auth.common.TiTiAuthException;
import com.titi.titi_common_lib.util.JacksonHelper;
import com.titi.titi_common_lib.util.JwtUtils;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

	private static final JwtService.Subject SUBJECT = JwtService.Subject.builder()
		.memberId(String.valueOf(1L))
		.deviceId(UUID.randomUUID().toString())
		.build();

	@Mock
	private JwtUtils jwtUtils;

	@InjectMocks
	private JwtService jwtService;

	@Test
	void generateAccessTokenTest() {
		// given
		given(jwtUtils.generate(any(JwtUtils.Payload.class))).willReturn("accessToken");

		// when
		final String accessToken = jwtService.generateAccessToken(SUBJECT, Instant.now());

		// then
		assertThat(accessToken).isNotBlank();
		verify(jwtUtils, times(1)).generate(any(JwtUtils.Payload.class));
	}

	@Test
	void generateRefreshTokenTest() {
		// given
		given(jwtUtils.generate(any(JwtUtils.Payload.class))).willReturn("refreshToken");

		// when
		final String refreshToken = jwtService.generateRefreshToken(SUBJECT, Instant.now());

		// then
		assertThat(refreshToken).isNotBlank();
		verify(jwtUtils, times(1)).generate(any(JwtUtils.Payload.class));
	}

	@Nested
	class ExtractSubject {

		@Test
		void successScenario() {
			// given
			final Claims mockClaims = mock(Claims.class);
			given(mockClaims.getSubject()).willReturn(JacksonHelper.toJson(SUBJECT));
			given(jwtUtils.getPayloads(anyString(), anyString())).willReturn(mockClaims);

			// when
			final String mockJwt = "jwt";
			final JwtService.Subject subject = jwtService.extractSubject(mockJwt, AuthConstants.ACCESS_TOKEN);

			// then
			assertThat(subject).isNotNull();
			assertThat(subject).usingRecursiveComparison().isEqualTo(SUBJECT);
			verify(jwtUtils, times(1)).getPayloads(anyString(), anyString());
		}

		@Test
		void failureScenario() {
			// given
			given(jwtUtils.getPayloads(anyString(), anyString())).willThrow(IllegalArgumentException.class);

			// when
			final String mockJwt = "jwt";
			final ThrowableAssert.ThrowingCallable throwingCallable = () -> jwtService.extractSubject(mockJwt, AuthConstants.ACCESS_TOKEN);

			// then
			assertThatCode(throwingCallable).isInstanceOf(TiTiAuthException.class);
			verify(jwtUtils, times(1)).getPayloads(anyString(), anyString());
		}

	}

}