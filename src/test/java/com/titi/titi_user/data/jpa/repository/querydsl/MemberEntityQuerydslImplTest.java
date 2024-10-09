package com.titi.titi_user.data.jpa.repository.querydsl;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.titi.JpaTestBase;
import com.titi.titi_user.data.jpa.entity.MemberEntity;
import com.titi.titi_user.data.jpa.repository.MemberEntityRepository;
import com.titi.titi_user.domain.member.MembershipType;
import com.titi.titi_user.domain.member.ProfileImage;

class MemberEntityQuerydslImplTest extends JpaTestBase {

	@Autowired
	private MemberEntityRepository memberEntityRepository;

	@Autowired
	private MemberEntityQuerydslImpl memberEntityQuerydsl;

	@Nested
	class FindByEntity {

		@Test
		void success() {
			// given
			final MemberEntity savedEntity = memberEntityRepository.save(
				MemberEntity.builder()
					.nickname("nickname")
					.accountId(1L)
					.membershipType(MembershipType.NORMAL)
					.profileImage(ProfileImage.defaultInstance())
					.build()
			);

			// when
			final MemberEntity entity = MemberEntity.builder()
				.id(savedEntity.getId())
				.accountId(1L)
				.nickname(savedEntity.getNickname())
				.hashcode(savedEntity.getHashcode())
				.membershipType(savedEntity.getMembershipType())
				.build();
			final Optional<MemberEntity> result = memberEntityQuerydsl.findByEntity(entity);

			// then
			assertThat(result.isPresent()).isTrue();
		}

		@Test
		void fail() {
			// given
			final MemberEntity entity = MemberEntity.builder()
				.id(1L)
				.accountId(1L)
				.nickname("nickname")
				.membershipType(MembershipType.NORMAL)
				.build();

			// when
			final Optional<MemberEntity> result = memberEntityQuerydsl.findByEntity(entity);

			// then
			assertThat(result.isEmpty()).isTrue();
		}

	}

}