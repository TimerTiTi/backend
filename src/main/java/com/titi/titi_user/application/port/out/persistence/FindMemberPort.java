package com.titi.titi_user.application.port.out.persistence;

import java.util.Optional;

import com.titi.titi_user.domain.member.Member;

public interface FindMemberPort {

	Optional<Member> invoke(Member member);

}
