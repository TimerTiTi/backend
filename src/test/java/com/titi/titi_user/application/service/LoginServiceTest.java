package com.titi.titi_user.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Base64;
import java.util.Optional;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import com.titi.titi_auth.domain.EncodedEncryptedPassword;
import com.titi.titi_crypto_lib.constant.AESCipherModes;
import com.titi.titi_crypto_lib.util.AESUtils;
import com.titi.titi_user.application.port.in.LoginUseCase;
import com.titi.titi_user.application.port.out.auth.GenerateAccessTokenPort;
import com.titi.titi_user.application.port.out.persistence.FindMemberPort;
import com.titi.titi_user.application.port.out.persistence.UpdateDeviceLastAccessPort;
import com.titi.titi_user.common.TiTiUserException;
import com.titi.titi_user.domain.device.Device;
import com.titi.titi_user.domain.member.Member;

@Disabled
@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

	private static final Long ID = 1L;
	private static final String JWT = "jwt";
	private static final String USERNAME = "test@gmail.com";
	private static final String RAW_PASSWORD = "qlalfqjsgh1!";
	private static final String BCRYPTED_PASSWORD = "bcryptedPassword";
	private static final String SECRET_KEY = "12345678901234567890123456789012";
	private static final byte[] ENCRYPTED_PASSWORD = AESUtils.encrypt(SECRET_KEY.getBytes(), RAW_PASSWORD.getBytes(), AESCipherModes.GCM_NO_PADDING);
	private static final String ENCODED_ENCRYPTED_PASSWORD = Base64.getUrlEncoder().encodeToString(ENCRYPTED_PASSWORD);

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private GenerateAccessTokenPort generateAccessTokenPort;

	@Mock
	private FindMemberPort findMemberPort;

	@Mock
	private UpdateDeviceLastAccessPort updateDeviceLastAccessPort;

	@InjectMocks
	private LoginService loginService;

	@BeforeEach
	void setup() {
		ReflectionTestUtils.setField(loginService, "secretKey", SECRET_KEY);
	}

	@Test
	void successfulScenario() {
		// given
		final Member mockMember = mock(Member.class);
		given(mockMember.id()).willReturn(ID);
		given(findMemberPort.invoke(any(Member.class))).willReturn(Optional.of(mockMember));
		given(passwordEncoder.matches(RAW_PASSWORD, BCRYPTED_PASSWORD)).willReturn(true);
		given(generateAccessTokenPort.invoke(any(GenerateAccessTokenPort.Command.class)))
			.willReturn(GenerateAccessTokenPort.Result.builder().accessToken(JWT).refreshToken(JWT).build());
		given(updateDeviceLastAccessPort.invoke(any(Device.class))).willReturn(mock(Device.class));

		// when
		final LoginUseCase.Result result = loginService.invoke(
			LoginUseCase.Command.builder()
				.username(USERNAME)
				.encodedEncryptedPassword(EncodedEncryptedPassword.builder().value(ENCODED_ENCRYPTED_PASSWORD).build())
				.build()
		);

		// then
		assertThat(result).isNotNull();
		assertThat(result.accessToken()).isNotBlank();
		assertThat(result.refreshToken()).isNotBlank();
		verify(findMemberPort, times(1)).invoke(any(Member.class));
		verify(passwordEncoder, times(1)).matches(RAW_PASSWORD, BCRYPTED_PASSWORD);
		verify(generateAccessTokenPort, times(1)).invoke(any(GenerateAccessTokenPort.Command.class));
	}

	@Test
	void failToFindMemberScenario() {
		// given
		given(findMemberPort.invoke(any(Member.class))).willReturn(Optional.empty());

		// when
		final ThrowableAssert.ThrowingCallable throwingCallable = () -> loginService.invoke(
			LoginUseCase.Command.builder()
				.username(USERNAME)
				.encodedEncryptedPassword(EncodedEncryptedPassword.builder().value(ENCODED_ENCRYPTED_PASSWORD).build())
				.build()
		);

		// then
		assertThatCode(throwingCallable).isInstanceOf(TiTiUserException.class);
		verify(findMemberPort, times(1)).invoke(any(Member.class));
		verify(passwordEncoder, never()).matches(RAW_PASSWORD, BCRYPTED_PASSWORD);
		verify(generateAccessTokenPort, never()).invoke(any(GenerateAccessTokenPort.Command.class));
	}

	@Test
	void failToValidatePasswordScenario() {
		// given
		final Member mockMember = mock(Member.class);
		given(findMemberPort.invoke(any(Member.class))).willReturn(Optional.of(mockMember));
		given(passwordEncoder.matches(RAW_PASSWORD, BCRYPTED_PASSWORD)).willReturn(false);

		// when
		final ThrowableAssert.ThrowingCallable throwingCallable = () -> loginService.invoke(
			LoginUseCase.Command.builder()
				.username(USERNAME)
				.encodedEncryptedPassword(EncodedEncryptedPassword.builder().value(ENCODED_ENCRYPTED_PASSWORD).build())
				.build()
		);

		// then
		assertThatCode(throwingCallable).isInstanceOf(TiTiUserException.class);
		verify(findMemberPort, times(1)).invoke(any(Member.class));
		verify(passwordEncoder, times(1)).matches(RAW_PASSWORD, BCRYPTED_PASSWORD);
		verify(generateAccessTokenPort, never()).invoke(any(GenerateAccessTokenPort.Command.class));
	}

}