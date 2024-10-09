package com.titi.titi_auth.adapter.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.titi.titi_auth.application.port.out.persistence.FindAccountPort;
import com.titi.titi_auth.data.jpa.entity.mapper.EntityMapper;
import com.titi.titi_auth.data.jpa.repository.AccountEntityRepository;
import com.titi.titi_auth.domain.Account;

@Component
@RequiredArgsConstructor
public class FindAccountPortAdapter implements FindAccountPort {

	private final AccountEntityRepository accountEntityRepository;

	@Override
	public Optional<Account> invoke(Account account) {
		return this.accountEntityRepository.findByEntity(EntityMapper.INSTANCE.toEntity(account))
			.map(EntityMapper.INSTANCE::toDomain);
	}

}
