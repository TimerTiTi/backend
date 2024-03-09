package com.titi.titi_user.application.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.titi.titi_user.application.port.in.CheckUsernameUseCase;
import com.titi.titi_user.application.port.out.persistence.FindMemberPort;
import com.titi.titi_user.domain.member.Member;

@Service
@RequiredArgsConstructor
class CheckUsernameService implements CheckUsernameUseCase {

	private final FindMemberPort findMemberPort;

	@Override
	public Result invoke(Command command) {
		final Member member = Member.builder().username(command.username()).build();
		return Result.builder()
			.isPresent(this.findMemberPort.invoke(member).isPresent())
			.build();
	}

}
