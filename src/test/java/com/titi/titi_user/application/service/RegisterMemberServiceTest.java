package com.titi.titi_user.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Base64;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.jsonwebtoken.Claims;

import com.titi.titi_common_lib.util.JwtUtils;
import com.titi.titi_crypto_lib.constant.AESCipherModes;
import com.titi.titi_crypto_lib.util.AESUtils;
import com.titi.titi_crypto_lib.util.HashingUtils;
import com.titi.titi_user.application.common.constant.UserConstants;
import com.titi.titi_user.application.port.in.RegisterMemberUseCase;
import com.titi.titi_user.application.port.out.auth.CreateAccountPort;
import com.titi.titi_user.application.port.out.persistence.SaveMemberPort;
import com.titi.titi_user.common.TiTiUserException;

@ExtendWith(MockitoExtension.class)
class RegisterMemberServiceTest {

	private static final String MOCK_AUTH_TOKEN = "mockAuthToken";
	private static final Claims MOCK_CLAIMS = mock(Claims.class);
	private static final String USERNAME = "test@gmail.com";
	private static final String AUTH_KEY = HashingUtils.hashSha256("ac_SU_E", USERNAME);
	private static final String RAW_PASSWORD = "qlalfqjsgh1!";
	private static final String SECRET_KEY = "12345678901234567890123456789012";
	private static final byte[] ENCRYPTED_PASSWORD = AESUtils.encrypt(SECRET_KEY.getBytes(), RAW_PASSWORD.getBytes(), AESCipherModes.GCM_NO_PADDING);
	private static final String ENCODED_ENCRYPTED_PASSWORD = Base64.getUrlEncoder().encodeToString(ENCRYPTED_PASSWORD);

	@Mock
	private JwtUtils jwtUtils;

	@Mock
	private SaveMemberPort saveMemberPort;

	@Mock
	private CreateAccountPort createAccountPort;

	@InjectMocks
	private RegisterMemberService registerMemberService;

	@Test
	void successfulScenario() {
		// given
		given(jwtUtils.getPayloads(MOCK_AUTH_TOKEN, UserConstants.AUTH_TOKEN)).willReturn(MOCK_CLAIMS);
		given(MOCK_CLAIMS.getSubject()).willReturn(AUTH_KEY);
		given(createAccountPort.invoke(any(CreateAccountPort.Command.class))).willReturn(CreateAccountPort.Result.builder().accountId(1L).build());

		// when
		final RegisterMemberUseCase.Command command = RegisterMemberUseCase.Command.builder()
			.username(USERNAME)
			.encodedEncryptedPassword(ENCODED_ENCRYPTED_PASSWORD)
			.nickname("nickname")
			.authToken(MOCK_AUTH_TOKEN)
			.build();
		final ThrowableAssert.ThrowingCallable throwingCallable = () -> registerMemberService.invoke(command);

		// then
		assertThatCode(throwingCallable).doesNotThrowAnyException();
	}

	@Test
	void failToValidateAuthTokenScenario() {
		// given
		given(jwtUtils.getPayloads(MOCK_AUTH_TOKEN, UserConstants.AUTH_TOKEN)).willThrow(IllegalArgumentException.class);

		// when
		final RegisterMemberUseCase.Command command = RegisterMemberUseCase.Command.builder()
			.username(USERNAME)
			.encodedEncryptedPassword(ENCODED_ENCRYPTED_PASSWORD)
			.nickname("nickname")
			.authToken(MOCK_AUTH_TOKEN)
			.build();
		final ThrowableAssert.ThrowingCallable throwingCallable = () -> registerMemberService.invoke(command);

		// then
		assertThatCode(throwingCallable).isInstanceOf(TiTiUserException.class);
	}

}