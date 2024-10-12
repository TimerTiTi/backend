package com.titi.titi_user.application.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import com.titi.titi_common_lib.util.JwtUtils;
import com.titi.titi_user.application.common.constant.UserConstants;
import com.titi.titi_user.application.port.in.RegisterMemberUseCase;
import com.titi.titi_user.application.port.out.auth.CreateAccountPort;
import com.titi.titi_user.application.port.out.persistence.SaveMemberPort;
import com.titi.titi_user.common.TiTiUserBusinessCodes;
import com.titi.titi_user.common.TiTiUserException;
import com.titi.titi_user.domain.member.Member;
import com.titi.titi_user.domain.member.MembershipType;
import com.titi.titi_user.domain.member.ProfileImage;

@Service
@RequiredArgsConstructor
class RegisterMemberService implements RegisterMemberUseCase {

	private final JwtUtils jwtUtils;
	private final SaveMemberPort saveMemberPort;
	private final CreateAccountPort createAccountPort;

	@Override
	@Transactional
	public void invoke(Command command) {
		this.validateAuthToken(command);
		final CreateAccountPort.Result createAccountResult = this.createAccountPort.invoke(
			CreateAccountPort.Command.builder()
				.username(command.username())
				.encodedEncryptedPassword(command.encodedEncryptedPassword())
				.build()
		);
		final Member member = Member.builder()
			.accountId(createAccountResult.accountId())
			.nickname(command.nickname())
			.profileImage(ProfileImage.defaultInstance())
			.membershipType(MembershipType.NORMAL)
			.build();
		this.saveMemberPort.invoke(member);
	}

	private void validateAuthToken(Command command) {
		try {
			final String authKey = this.jwtUtils.getPayloads(command.authToken(), UserConstants.AUTH_TOKEN).getSubject();
			command.validateAuthKey(authKey);
		} catch (IllegalArgumentException e) {
			throw new TiTiUserException(TiTiUserBusinessCodes.REGISTER_MEMBER_FAILURE_INVALID_AUTH_TOKEN);
		}
	}

}
