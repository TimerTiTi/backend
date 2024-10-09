package com.titi.titi_auth.adapter.out.persistence.querydsl;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.titi.JpaTestBase;
import com.titi.titi_auth.data.jpa.entity.AccountEntity;
import com.titi.titi_auth.data.jpa.repository.AccountEntityRepository;
import com.titi.titi_auth.data.jpa.repository.querydsl.AccountEntityQuerydslImpl;
import com.titi.titi_auth.domain.AccountStatus;
import com.titi.titi_auth.domain.Authority;

class AccountEntityQuerydslImplTest extends JpaTestBase {

	@Autowired
	private AccountEntityRepository accountEntityRepository;

	@Autowired
	private AccountEntityQuerydslImpl accountEntityQuerydsl;

	@Nested
	class FindByEntity {

		@Test
		void success() {
			// given
			final AccountEntity savedEntity = accountEntityRepository.save(
				AccountEntity.builder()
					.username("test@gmail.com")
					.password("password")
					.accountStatus(AccountStatus.ACTIVATED)
					.authority(Authority.MEMBER)
					.build()
			);

			// when
			final AccountEntity entity = AccountEntity.builder()
				.id(savedEntity.getId())
				.username(savedEntity.getUsername())
				.authority(savedEntity.getAuthority())
				.build();
			final Optional<AccountEntity> result = accountEntityQuerydsl.findByEntity(entity);

			// then
			assertThat(result.isPresent()).isTrue();
		}

		@Test
		void fail() {
			// given
			final AccountEntity entity = AccountEntity.builder()
				.id(1L)
				.username("test@gmail.com")
				.accountStatus(AccountStatus.ACTIVATED)
				.authority(Authority.MEMBER)
				.build();

			// when
			final Optional<AccountEntity> result = accountEntityQuerydsl.findByEntity(entity);

			// then
			assertThat(result.isEmpty()).isTrue();
		}

	}

}