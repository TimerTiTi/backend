package com.titi.titi_user.adapter.out.auth;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.titi.exception.TiTiException;
import com.titi.titi_auth.application.port.in.CreateAccountUseCase;
import com.titi.titi_user.application.port.out.auth.CreateAccountPort;
import com.titi.titi_user.common.TiTiUserBusinessCodes;
import com.titi.titi_user.common.TiTiUserException;

@Component
@RequiredArgsConstructor
public class CreateAccountPortAdapter implements CreateAccountPort {

	private final CreateAccountUseCase createAccountUseCase;

	@Override
	public Result invoke(Command command) {
		try {
			final CreateAccountUseCase.Result createAccountResult = this.createAccountUseCase.invoke(
				CreateAccountUseCase.Command.builder()
					.username(command.username())
					.encodedEncryptedPassword(command.encodedEncryptedPassword())
					.build()
			);
			return Result.builder()
				.accountId(createAccountResult.accountId())
				.build();
		} catch (TiTiException e) {
			throw new TiTiUserException(TiTiUserBusinessCodes.UNAVAILABLE_USERNAME);
		}
	}

}
