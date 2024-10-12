package com.titi.titi_auth.data.jpa.repository.querydsl;

import static com.titi.titi_auth.data.jpa.entity.QAccountEntity.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import com.titi.titi_auth.data.jpa.entity.AccountEntity;

@Repository
@RequiredArgsConstructor
public class AccountEntityQuerydslImpl implements AccountEntityQuerydsl {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Optional<AccountEntity> findByEntity(AccountEntity entity) {
		return Optional.ofNullable(
			jpaQueryFactory
				.selectFrom(accountEntity)
				.where(
					idEq(entity),
					usernameEq(entity),
					authorityEq(entity),
					accountStatusEq(entity)
				)
				.fetchOne()
		);
	}

	private BooleanExpression idEq(AccountEntity entity) {
		return entity.getId() != null ? accountEntity.id.eq(entity.getId()) : null;
	}

	private BooleanExpression usernameEq(AccountEntity entity) {
		return entity.getUsername() != null ? accountEntity.username.eq(entity.getUsername()) : null;
	}

	private BooleanExpression authorityEq(AccountEntity entity) {
		return entity.getAuthority() != null ? accountEntity.authority.eq(entity.getAuthority()) : null;
	}

	private BooleanExpression accountStatusEq(AccountEntity entity) {
		return entity.getAccountStatus() != null ? accountEntity.accountStatus.eq(entity.getAccountStatus()) : null;
	}

}
