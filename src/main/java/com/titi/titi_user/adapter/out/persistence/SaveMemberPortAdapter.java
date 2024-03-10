package com.titi.titi_user.adapter.out.persistence;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.titi.titi_user.application.port.out.persistence.SaveMemberPort;
import com.titi.titi_user.data.jpa.entity.MemberEntity;
import com.titi.titi_user.data.jpa.entity.mapper.EntityMapper;
import com.titi.titi_user.data.jpa.repository.MemberEntityRepository;
import com.titi.titi_user.domain.member.Member;

@Component
@RequiredArgsConstructor
class SaveMemberPortAdapter implements SaveMemberPort {

	private final MemberEntityRepository memberEntityRepository;

	@Override
	public Member invoke(Member member) {
		final MemberEntity memberEntity = memberEntityRepository.save(EntityMapper.INSTANCE.toEntity(member));
		return EntityMapper.INSTANCE.toDomain(memberEntity);
	}

}
