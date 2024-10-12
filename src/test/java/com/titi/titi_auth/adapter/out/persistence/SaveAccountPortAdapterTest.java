package com.titi.titi_auth.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.titi.titi_auth.data.jpa.entity.AccountEntity;
import com.titi.titi_auth.data.jpa.entity.mapper.EntityMapper;
import com.titi.titi_auth.data.jpa.repository.AccountEntityRepository;
import com.titi.titi_auth.domain.Account;
import com.titi.titi_auth.domain.AccountStatus;
import com.titi.titi_auth.domain.Authority;

@ExtendWith(MockitoExtension.class)
class SaveAccountPortAdapterTest {

	@Mock
	private AccountEntityRepository accountEntityRepository;

	@InjectMocks
	private SaveAccountPortAdapter saveAccountPortAdapter;

	@Test
	void whenMemberIsisNotNullThenThrowsIllegalArgumentException() {
		// given
		final Account account = Account.builder().id(1L).build();

		// when
		final ThrowableAssert.ThrowingCallable throwingCallable = () -> saveAccountPortAdapter.invoke(account);

		// then
		assertThatCode(throwingCallable).isInstanceOf(IllegalArgumentException.class);
		verify(accountEntityRepository, never()).save(any());
	}

	@Test
	void whenMemberIsisNullThenSuccessfullySaveMember() {
		// given
		final Account account = Account.builder()
			.username("test@gmail.com")
			.encodedEncryptedPassword("encodedEncryptedPassword")
			.authority(Authority.MEMBER)
			.accountStatus(AccountStatus.ACTIVATED)
			.build();

		final AccountEntity accountEntity = EntityMapper.INSTANCE.toEntity(account);
		final long id = 1L;
		final AccountEntity savedAccountEntity = accountEntity.toBuilder().id(id).build();
		given(accountEntityRepository.save(any())).willReturn(savedAccountEntity);

		// when
		final Account result = saveAccountPortAdapter.invoke(account);

		// then
		assertThat(result).isNotNull();
		assertThat(result.id()).isEqualTo(id);
		verify(accountEntityRepository, times(1)).save(any());
	}

}