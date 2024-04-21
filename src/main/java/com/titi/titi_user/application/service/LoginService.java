package com.titi.titi_user.application.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import com.titi.titi_user.application.port.in.LoginUseCase;
import com.titi.titi_user.application.port.out.internal.GenerateAccessTokenPort;
import com.titi.titi_user.application.port.out.persistence.FindMemberPort;
import com.titi.titi_user.application.port.out.persistence.UpdateDeviceLastAccessPort;
import com.titi.titi_user.common.TiTiUserBusinessCodes;
import com.titi.titi_user.common.TiTiUserException;
import com.titi.titi_user.domain.device.Device;
import com.titi.titi_user.domain.member.Member;

@Service
@RequiredArgsConstructor
class LoginService implements LoginUseCase {

	private final FindMemberPort findMemberPort;
	private final PasswordEncoder passwordEncoder;
	private final GenerateAccessTokenPort generateAccessTokenPort;
	private final UpdateDeviceLastAccessPort updateDeviceLastAccessPort;
	@Value("${crypto.secret-key}")
	private String secretKey;

	@Override
	@Transactional
	public Result invoke(Command command) {
		final Member member = this.findMemberPort.invoke(Member.builder().username(command.username()).build())
			.orElseThrow(() -> new TiTiUserException(TiTiUserBusinessCodes.LOGIN_FAILURE_MISMATCHED_MEMBER_INFORMATION));
		final String rawPassword = command.encodedEncryptedPassword().getRawPassword(this.secretKey.getBytes());
		this.validatePassword(rawPassword, member);

		final String generatedDeviceId = command.deviceId() == null ? UUID.randomUUID().toString() : null;
		final Device device = Device.builder()
			.deviceId(command.deviceId() == null ? generatedDeviceId : command.deviceId())
			.member(member)
			.deviceType(command.deviceType())
			.lastAccessedAt(LocalDateTime.now())
			.build();

		final GenerateAccessTokenPort.Result result = this.generateAccessTokenPort.invoke(
			GenerateAccessTokenPort.Command.builder()
				.memberId(String.valueOf(member.id()))
				.deviceId(device.deviceId())
				.build()
		);

		this.updateDeviceLastAccessPort.invoke(device);

		return Result.builder()
			.accessToken(result.accessToken())
			.refreshToken(result.refreshToken())
			.deviceId(generatedDeviceId)
			.build();
	}

	private void validatePassword(String rawPassword, Member member) {
		if (!this.passwordEncoder.matches(rawPassword, member.password())) {
			throw new TiTiUserException(TiTiUserBusinessCodes.LOGIN_FAILURE_MISMATCHED_MEMBER_INFORMATION);
		}
	}

}
