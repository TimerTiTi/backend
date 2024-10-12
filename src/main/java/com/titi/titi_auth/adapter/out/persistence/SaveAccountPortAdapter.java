package com.titi.titi_auth.adapter.out.persistence;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.titi.titi_auth.application.port.out.persistence.SaveAccountPort;
import com.titi.titi_auth.data.jpa.entity.AccountEntity;
import com.titi.titi_auth.data.jpa.entity.mapper.EntityMapper;
import com.titi.titi_auth.data.jpa.repository.AccountEntityRepository;
import com.titi.titi_auth.domain.Account;

@Component
@RequiredArgsConstructor
public class SaveAccountPortAdapter implements SaveAccountPort {

	private final AccountEntityRepository accountEntityRepository;

	@Override
	public Account invoke(Account account) {
		if (account.id() != null) {
			throw new IllegalArgumentException("account.id() must be null.");
		}
		final AccountEntity accountEntity = accountEntityRepository.save(EntityMapper.INSTANCE.toEntity(account));
		return EntityMapper.INSTANCE.toDomain(accountEntity);
	}

}