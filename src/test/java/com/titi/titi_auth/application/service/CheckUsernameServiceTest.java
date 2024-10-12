package com.titi.titi_auth.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.titi.titi_auth.application.port.in.CheckUsernameUseCase;
import com.titi.titi_auth.application.port.out.persistence.FindAccountPort;
import com.titi.titi_auth.domain.Account;

@ExtendWith(MockitoExtension.class)
class CheckUsernameServiceTest {
	@Mock
	private FindAccountPort findAccountPort;

	@InjectMocks
	private CheckUsernameService checkUsernameService;

	@Test
	void whenAccountIsPresentWithUsernameThenReturnTrue() {
		// given
		final String username = "test@gmail.com";
		given(findAccountPort.invoke(any())).willReturn(Optional.of(mock(Account.class)));

		// when
		final CheckUsernameUseCase.Result result = checkUsernameService.invoke(CheckUsernameUseCase.Command.builder().username(username).build());

		// then
		assertThat(result).isNotNull();
		assertThat(result.isPresent()).isTrue();
	}

	@Test
	void whenMemberIsNotPresentWithUsernameThenReturnFalse() {
		// given
		final String username = "test@gmail.com";
		given(findAccountPort.invoke(any())).willReturn(Optional.empty());

		// when
		final CheckUsernameUseCase.Result result = checkUsernameService.invoke(CheckUsernameUseCase.Command.builder().username(username).build());

		// then
		assertThat(result).isNotNull();
		assertThat(result.isPresent()).isFalse();
	}
}