package com.titi.titi_user.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.titi.titi_user.data.jpa.entity.MemberEntity;
import com.titi.titi_user.data.jpa.entity.mapper.EntityMapper;
import com.titi.titi_user.data.jpa.repository.MemberEntityRepository;
import com.titi.titi_user.domain.member.Member;
import com.titi.titi_user.domain.member.MembershipType;
import com.titi.titi_user.domain.member.ProfileImage;

@ExtendWith(MockitoExtension.class)
class SaveMemberPortAdapterTest {

	@Mock
	private MemberEntityRepository memberEntityRepository;

	@InjectMocks
	private SaveMemberPortAdapter saveMemberPortAdapter;

	@Test
	void whenMemberIsisNotNullThenThrowsIllegalArgumentException() {
		// given
		final Member member = Member.builder().id(1L).build();

		// when
		final ThrowableAssert.ThrowingCallable throwingCallable = () -> saveMemberPortAdapter.invoke(member);

		// then
		assertThatCode(throwingCallable).isInstanceOf(IllegalArgumentException.class);
		verify(memberEntityRepository, never()).save(any());
	}

	@Test
	void whenMemberIsisNullThenSuccessfullySaveMember() {
		// given
		final Member member = Member.builder()
			.nickname("nickname")
			.profileImage(ProfileImage.defaultInstance())
			.membershipType(MembershipType.NORMAL)
			.build();

		final MemberEntity memberEntity = EntityMapper.INSTANCE.toEntity(member);
		final long id = 1L;
		final MemberEntity saveMemberEntity = memberEntity.toBuilder().id(id).build();
		given(memberEntityRepository.save(any())).willReturn(saveMemberEntity);

		// when
		final Member result = saveMemberPortAdapter.invoke(member);

		// then
		assertThat(result).isNotNull();
		assertThat(result.id()).isEqualTo(id);
		verify(memberEntityRepository, times(1)).save(any());
	}

}