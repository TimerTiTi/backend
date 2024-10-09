package com.titi.titi_auth.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.titi.titi_auth.data.jpa.entity.AccountEntity;
import com.titi.titi_auth.data.jpa.repository.AccountEntityRepository;
import com.titi.titi_auth.domain.Account;
import com.titi.titi_auth.domain.AccountStatus;
import com.titi.titi_auth.domain.Authority;

@ExtendWith(MockitoExtension.class)
class FindAccountPortAdapterTest {

	@Mock
	private AccountEntityRepository accountEntityRepository;

	@InjectMocks
	private FindAccountPortAdapter findAccountPortAdapter;

	@Test
	void whenAccountEntityIsPresent() {
		// given
		final String username = "test@gmail.com";
		final AccountEntity mockAccountEntity = AccountEntity.builder()
			.id(1L)
			.username(username)
			.password("password")
			.accountStatus(AccountStatus.ACTIVATED)
			.authority(Authority.MEMBER)
			.build();
		given(accountEntityRepository.findByEntity(any())).willReturn(Optional.of(mockAccountEntity));

		// when
		final Account account = Account.builder().username(username).build();
		final Optional<Account> result = findAccountPortAdapter.invoke(account);

		// then
		assertThat(result.isPresent()).isTrue();
	}

	@Test
	void whenAccountEntityIsEmpty() {
		// given
		final String username = "test@gmail.com";
		given(accountEntityRepository.findByEntity(any())).willReturn(Optional.empty());

		// when
		final Account account = Account.builder().username(username).build();
		final Optional<Account> result = findAccountPortAdapter.invoke(account);

		// then
		assertThat(result.isEmpty()).isTrue();
	}

}