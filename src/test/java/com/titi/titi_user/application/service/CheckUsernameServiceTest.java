package com.titi.titi_user.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.titi.titi_user.application.port.in.CheckUsernameUseCase;
import com.titi.titi_user.application.port.out.persistence.FindMemberPort;
import com.titi.titi_user.domain.member.Member;

@ExtendWith(MockitoExtension.class)
class CheckUsernameServiceTest {

	@Mock
	private FindMemberPort findMemberPort;

	@InjectMocks
	private CheckUsernameService checkUsernameService;

	@Test
	void whenMemberIsPresentWithUsernameThenReturnTrue() {
	    // given
		final String username = "test@gmail.com";
		given(findMemberPort.invoke(any())).willReturn(Optional.of(mock(Member.class)));

	    // when
		final CheckUsernameUseCase.Result result = checkUsernameService.invoke(CheckUsernameUseCase.Command.builder().username(username).build());

		// then
		assertThat(result).isNotNull();
		assertThat(result.isPresent()).isTrue();
	}

	@Test
	void whenMemberIsNotPresentWithUsernameThenReturnFalse() {
	    // given
		final String username = "test@gmail.com";
		given(findMemberPort.invoke(any())).willReturn(Optional.empty());

	    // when
		final CheckUsernameUseCase.Result result = checkUsernameService.invoke(CheckUsernameUseCase.Command.builder().username(username).build());

		// then
		assertThat(result).isNotNull();
		assertThat(result.isPresent()).isFalse();
	}

}