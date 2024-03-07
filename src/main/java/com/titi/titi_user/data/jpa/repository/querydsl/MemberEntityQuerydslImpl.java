package com.titi.titi_user.data.jpa.repository.querydsl;

import static com.titi.titi_user.data.jpa.entity.QMemberEntity.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import com.titi.titi_user.data.jpa.entity.MemberEntity;

@Repository
@RequiredArgsConstructor
class MemberEntityQuerydslImpl implements MemberEntityQuerydsl {

	private final JPAQueryFactory jpaQueryFactory;

	public Optional<MemberEntity> findByEntity(MemberEntity entity) {
		return Optional.ofNullable(
			jpaQueryFactory
				.selectFrom(memberEntity)
				.where(
					idEq(entity),
					usernameEq(entity),
					nicknameEq(entity),
					hashcodeEq(entity),
					authorityEq(entity),
					accountStatusEq(entity),
					membershipTypeEq(entity)
				)
				.fetchOne()
		);
	}

	private BooleanExpression membershipTypeEq(MemberEntity entity) {
		return entity.getMembershipType() != null ? memberEntity.membershipType.eq(entity.getMembershipType()) : null;
	}

	private BooleanExpression accountStatusEq(MemberEntity entity) {
		return entity.getAccountStatus() != null ? memberEntity.accountStatus.eq(entity.getAccountStatus()) : null;
	}

	private BooleanExpression authorityEq(MemberEntity entity) {
		return entity.getAuthority() != null ? memberEntity.authority.eq(entity.getAuthority()) : null;
	}

	private BooleanExpression hashcodeEq(MemberEntity entity) {
		return entity.getHashcode() != null ? memberEntity.hashcode.eq(entity.getHashcode()) : null;
	}

	private BooleanExpression nicknameEq(MemberEntity entity) {
		return entity.getNickname() != null ? memberEntity.nickname.eq(entity.getNickname()) : null;
	}

	private BooleanExpression usernameEq(MemberEntity entity) {
		return entity.getUsername() != null ? memberEntity.username.eq(entity.getUsername()) : null;
	}

	private BooleanExpression idEq(MemberEntity entity) {
		return entity.getId() != null ? memberEntity.id.eq(entity.getId()) : null;
	}

}
