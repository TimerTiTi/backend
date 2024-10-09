package com.titi.titi_auth.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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

import com.titi.titi_auth.application.port.in.CreateAccountUseCase;
import com.titi.titi_auth.application.port.out.persistence.FindAccountPort;
import com.titi.titi_auth.application.port.out.persistence.SaveAccountPort;
import com.titi.titi_auth.common.TiTiAuthException;
import com.titi.titi_auth.domain.Account;
import com.titi.titi_crypto_lib.constant.AESCipherModes;
import com.titi.titi_crypto_lib.util.AESUtils;

@ExtendWith(MockitoExtension.class)
class CreateAccountServiceTest {

	private static final String USERNAME = "test@gmail.com";
	private static final String RAW_PASSWORD = "qlalfqjsgh1!";
	private static final String SECRET_KEY = "12345678901234567890123456789012";
	private static final byte[] ENCRYPTED_PASSWORD = AESUtils.encrypt(SECRET_KEY.getBytes(), RAW_PASSWORD.getBytes(), AESCipherModes.GCM_NO_PADDING);
	private static final String ENCODED_ENCRYPTED_PASSWORD = Base64.getUrlEncoder().encodeToString(ENCRYPTED_PASSWORD);

	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private FindAccountPort findAccountPort;
	@Mock
	private SaveAccountPort saveAccountPort;
	@InjectMocks
	private CreateAccountService createAccountService;

	@BeforeEach
	void setup() {
		ReflectionTestUtils.setField(createAccountService, "secretKey", SECRET_KEY);
	}

	@Test
	void successfulScenario() {
		// given
		given(findAccountPort.invoke(any(Account.class))).willReturn(Optional.empty());
		given(passwordEncoder.encode(RAW_PASSWORD)).willReturn("encodedEncryptedPassword");
		given(saveAccountPort.invoke(any(Account.class))).willReturn(Account.builder().id(1L).build());

		// when
		final CreateAccountUseCase.Command command = CreateAccountUseCase.Command.builder()
			.username(USERNAME)
			.encodedEncryptedPassword(ENCODED_ENCRYPTED_PASSWORD)
			.build();
		final ThrowableAssert.ThrowingCallable throwingCallable = () -> createAccountService.invoke(command);

		// then
		assertThatCode(throwingCallable).doesNotThrowAnyException();
	}

	@Test
	void failToValidateUsernameScenario() {
		// given
		given(findAccountPort.invoke(any(Account.class))).willReturn(Optional.of(mock(Account.class)));

		// when
		final CreateAccountUseCase.Command command = CreateAccountUseCase.Command.builder()
			.username(USERNAME)
			.encodedEncryptedPassword(ENCODED_ENCRYPTED_PASSWORD)
			.build();
		final ThrowableAssert.ThrowingCallable throwingCallable = () -> createAccountService.invoke(command);

		// then
		assertThatCode(throwingCallable).isInstanceOf(TiTiAuthException.class);
	}

}