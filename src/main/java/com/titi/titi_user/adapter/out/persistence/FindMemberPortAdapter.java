package com.titi.titi_user.adapter.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.titi.titi_user.application.port.out.persistence.FindMemberPort;
import com.titi.titi_user.data.jpa.entity.mapper.EntityMapper;
import com.titi.titi_user.data.jpa.repository.MemberEntityRepository;
import com.titi.titi_user.domain.member.Member;

@Component
@RequiredArgsConstructor
class FindMemberPortAdapter implements FindMemberPort {

	private final MemberEntityRepository memberEntityRepository;

	@Override
	public Optional<Member> invoke(Member member) {
		return this.memberEntityRepository.findByEntity(EntityMapper.INSTANCE.toEntity(member))
			.map(EntityMapper.INSTANCE::toDomain);
	}

}
