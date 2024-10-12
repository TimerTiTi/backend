package com.titi.titi_auth.application.service;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.titi.titi_auth.application.port.in.CreateAccountUseCase;
import com.titi.titi_auth.application.port.out.persistence.FindAccountPort;
import com.titi.titi_auth.application.port.out.persistence.SaveAccountPort;
import com.titi.titi_auth.common.TiTiAuthBusinessCodes;
import com.titi.titi_auth.common.TiTiAuthException;
import com.titi.titi_auth.domain.Account;
import com.titi.titi_auth.domain.AccountStatus;
import com.titi.titi_auth.domain.Authority;
import com.titi.titi_auth.domain.EncodedEncryptedPassword;

@Service
@RequiredArgsConstructor
public class CreateAccountService implements CreateAccountUseCase {

	private final PasswordEncoder passwordEncoder;
	private final FindAccountPort findAccountPort;
	private final SaveAccountPort saveAccountPort;
	@Value("${crypto.secret-key}")
	private String secretKey;

	@Override
	public Result invoke(Command command) {
		this.validateUsername(command.username());
		final String rawPassword = EncodedEncryptedPassword.builder()
			.value(command.encodedEncryptedPassword())
			.build()
			.getRawPassword(this.secretKey.getBytes(StandardCharsets.UTF_8));
		final String encodedEncryptedPassword = this.passwordEncoder.encode(rawPassword);
		final Account account = this.saveAccountPort.invoke(
			Account.builder()
				.username(command.username())
				.encodedEncryptedPassword(encodedEncryptedPassword)
				.accountStatus(AccountStatus.ACTIVATED)
				.authority(Authority.MEMBER)
				.build()
		);
		return Result.builder()
			.accountId(account.id())
			.build();
	}

	private void validateUsername(String username) {
		final Account account = Account.builder().username(username).build();
		if (this.findAccountPort.invoke(account).isPresent()) {
			throw new TiTiAuthException(TiTiAuthBusinessCodes.UNAVAILABLE_USERNAME);
		}
	}

}
