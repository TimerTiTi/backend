package com.titi.titi_user.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Base64;
import java.util.Optional;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import io.jsonwebtoken.Claims;

import com.titi.titi_common_lib.util.JwtUtils;
import com.titi.titi_crypto_lib.constant.AESCipherModes;
import com.titi.titi_crypto_lib.util.AESUtils;
import com.titi.titi_crypto_lib.util.HashingUtils;
import com.titi.titi_user.application.port.in.RegisterMemberUseCase;
import com.titi.titi_user.application.port.out.persistence.FindMemberPort;
import com.titi.titi_user.application.port.out.persistence.SaveMemberPort;
import com.titi.titi_user.common.TiTiUserException;
import com.titi.titi_user.domain.member.Member;

@ExtendWith(MockitoExtension.class)
class RegisterMemberServiceTest {

	private static final String MOCK_AUTH_TOKEN = "mockAuthToken";
	private static final Claims MOCK_CLAIMS = mock(Claims.class);
	private static final String USERNAME = "test@gmail.com";
	private static final String AUTH_KEY = HashingUtils.hashSha256("ac_SU_E", USERNAME);
	private static final String RAW_PASSWORD = "qlalfqjsgh1!";
	private static final String WRAPPING_KEY = "12345678901234567890123456789012";
	private static final byte[] WRAPPED_PASSWORD = AESUtils.encrypt(WRAPPING_KEY.getBytes(), RAW_PASSWORD.getBytes(), AESCipherModes.GCM_NO_PADDING);
	private static final String ENCODED_WRAPPED_PASSWORD = Base64.getUrlEncoder().encodeToString(WRAPPED_PASSWORD);

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private JwtUtils jwtUtils;

	@Mock
	private FindMemberPort findMemberPort;

	@Mock
	private SaveMemberPort saveMemberPort;

	@InjectMocks
	private RegisterMemberService registerMemberService;

	@BeforeEach
	void setup() {
		ReflectionTestUtils.setField(registerMemberService, "wrappingKey", WRAPPING_KEY);
	}

	@Test
	void successfulScenario() {
		// given
		given(jwtUtils.getPayloads(MOCK_AUTH_TOKEN)).willReturn(MOCK_CLAIMS);
		given(MOCK_CLAIMS.getSubject()).willReturn(AUTH_KEY);
		given(findMemberPort.invoke(any())).willReturn(Optional.empty());
		given(passwordEncoder.encode(RAW_PASSWORD)).willReturn("encryptedPassword");

		// when
		final RegisterMemberUseCase.Command command = RegisterMemberUseCase.Command.builder()
			.username(USERNAME)
			.encodedWrappedPassword(ENCODED_WRAPPED_PASSWORD)
			.nickname("nickname")
			.authToken(MOCK_AUTH_TOKEN)
			.build();
		final ThrowableAssert.ThrowingCallable throwingCallable = () -> registerMemberService.invoke(command);

		// then
		assertThatCode(throwingCallable).doesNotThrowAnyException();
	}

	@Test
	void failToValidateUsernameScenario() {
		// given
		given(jwtUtils.getPayloads(MOCK_AUTH_TOKEN)).willReturn(MOCK_CLAIMS);
		given(MOCK_CLAIMS.getSubject()).willReturn(AUTH_KEY);
		given(findMemberPort.invoke(any())).willReturn(Optional.of(mock(Member.class)));

		// when
		final RegisterMemberUseCase.Command command = RegisterMemberUseCase.Command.builder()
			.username(USERNAME)
			.encodedWrappedPassword(ENCODED_WRAPPED_PASSWORD)
			.nickname("nickname")
			.authToken(MOCK_AUTH_TOKEN)
			.build();
		final ThrowableAssert.ThrowingCallable throwingCallable = () -> registerMemberService.invoke(command);

		// then
		assertThatCode(throwingCallable).isInstanceOf(TiTiUserException.class);
	}

	@Test
	void failToValidateAuthTokenScenario() {
		// given
		given(jwtUtils.getPayloads(MOCK_AUTH_TOKEN)).willThrow(IllegalArgumentException.class);

		// when
		final RegisterMemberUseCase.Command command = RegisterMemberUseCase.Command.builder()
			.username(USERNAME)
			.encodedWrappedPassword(ENCODED_WRAPPED_PASSWORD)
			.nickname("nickname")
			.authToken(MOCK_AUTH_TOKEN)
			.build();
		final ThrowableAssert.ThrowingCallable throwingCallable = () -> registerMemberService.invoke(command);

		// then
		assertThatCode(throwingCallable).isInstanceOf(TiTiUserException.class);
	}

}