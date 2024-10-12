package com.titi.titi_auth.data.jpa.repository.querydsl;

import java.util.Optional;

import com.titi.titi_auth.data.jpa.entity.AccountEntity;

public interface AccountEntityQuerydsl {

	Optional<AccountEntity> findByEntity(AccountEntity accountEntity);

}
