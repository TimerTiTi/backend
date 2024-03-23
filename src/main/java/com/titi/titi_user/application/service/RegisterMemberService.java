package com.titi.titi_user.application.service;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.titi.titi_common_lib.util.JwtUtils;
import com.titi.titi_user.application.port.in.RegisterMemberUseCase;
import com.titi.titi_user.application.port.out.persistence.FindMemberPort;
import com.titi.titi_user.application.port.out.persistence.SaveMemberPort;
import com.titi.titi_user.common.TiTiUserBusinessCodes;
import com.titi.titi_user.common.TiTiUserException;
import com.titi.titi_user.domain.member.AccountStatus;
import com.titi.titi_user.domain.member.Authority;
import com.titi.titi_user.domain.member.Member;
import com.titi.titi_user.domain.member.MembershipType;
import com.titi.titi_user.domain.member.ProfileImage;

@Service
@RequiredArgsConstructor
class RegisterMemberService implements RegisterMemberUseCase {

	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtUtils;
	private final FindMemberPort findMemberPort;
	private final SaveMemberPort saveMemberPort;
	@Value("${crypto.secret-key}")
	private String secretKey;

	@Override
	public void invoke(Command command) {
		this.validateAuthToken(command);
		this.validateUsername(command.username());
		final String rawPassword = command.encodedEncryptedPassword().getRawPassword(this.secretKey.getBytes(StandardCharsets.UTF_8));
		final String encryptedPassword = this.passwordEncoder.encode(rawPassword);
		final Member member = Member.builder()
			.username(command.username())
			.password(encryptedPassword)
			.nickname(command.nickname())
			.profileImage(ProfileImage.defaultInstance())
			.membershipType(MembershipType.NORMAL)
			.accountStatus(AccountStatus.ACTIVATED)
			.authority(Authority.MEMBER)
			.build();
		this.saveMemberPort.invoke(member);
	}

	private void validateUsername(String username) {
		final Member member = Member.builder().username(username).build();
		if (this.findMemberPort.invoke(member).isPresent()) {
			throw new TiTiUserException(TiTiUserBusinessCodes.REGISTER_MEMBER_FAILURE_ALREADY_EXISTS_USERNAME);
		}
	}

	private void validateAuthToken(Command command) {
		try {
			final String authKey = this.jwtUtils.getPayloads(command.authToken()).getSubject();
			command.validateAuthKey(authKey);
		} catch (IllegalArgumentException e) {
			throw new TiTiUserException(TiTiUserBusinessCodes.REGISTER_MEMBER_FAILURE_INVALID_AUTH_TOKEN);
		}
	}

}
