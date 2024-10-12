package com.titi.titi_auth.application.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.titi.titi_auth.application.port.in.CheckUsernameUseCase;
import com.titi.titi_auth.application.port.out.persistence.FindAccountPort;
import com.titi.titi_auth.domain.Account;

@Service
@RequiredArgsConstructor
public class CheckUsernameService implements CheckUsernameUseCase {

	private final FindAccountPort findAccountPort;

	@Override
	public Result invoke(Command command) {
		final Account account = Account.builder().username(command.username()).build();
		return CheckUsernameUseCase.Result.builder()
			.isPresent(this.findAccountPort.invoke(account).isPresent())
			.build();
	}

}
