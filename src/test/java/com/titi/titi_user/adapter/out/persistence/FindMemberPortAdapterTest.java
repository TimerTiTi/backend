package com.titi.titi_user.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.titi.titi_user.data.jpa.entity.MemberEntity;
import com.titi.titi_user.data.jpa.repository.MemberEntityRepository;
import com.titi.titi_user.domain.member.AccountStatus;
import com.titi.titi_user.domain.member.Authority;
import com.titi.titi_user.domain.member.Member;
import com.titi.titi_user.domain.member.MembershipType;
import com.titi.titi_user.domain.member.ProfileImage;

@ExtendWith(MockitoExtension.class)
class FindMemberPortAdapterTest {

	@Mock
	private MemberEntityRepository memberEntityRepository;

	@InjectMocks
	private FindMemberPortAdapter findMemberPortAdapter;

	@Test
	void whenMemberEntityIsPresent() {
		// given
		final String username = "test@gmail.com";
		final MemberEntity mockMemberEntity = MemberEntity.builder()
			.id(1L)
			.username(username)
			.password("password")
			.accountStatus(AccountStatus.ACTIVATED)
			.authority(Authority.MEMBER)
			.hashcode("123456")
			.nickname("nickname")
			.membershipType(MembershipType.NORMAL)
			.profileImage(ProfileImage.defaultInstance())
			.build();
		given(memberEntityRepository.findByEntity(any())).willReturn(Optional.of(mockMemberEntity));

		// when
		final Member member = Member.builder().username(username).build();
		final Optional<Member> result = findMemberPortAdapter.invoke(member);

		// then
		assertThat(result.isPresent()).isTrue();
	}

	@Test
	void whenMemberEntityIsEmpty() {
		// given
		final String username = "test@gmail.com";
		given(memberEntityRepository.findByEntity(any())).willReturn(Optional.empty());

		// when
		final Member member = Member.builder().username(username).build();
		final Optional<Member> result = findMemberPortAdapter.invoke(member);

		// then
		assertThat(result.isEmpty()).isTrue();
	}

}