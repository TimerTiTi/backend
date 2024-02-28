package com.titi.titi_auth.application.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.titi.titi_auth.application.port.in.GenerateAuthCodeUseCase;
import com.titi.titi_auth.application.port.out.PutAuthCodePort;
import com.titi.titi_auth.application.port.out.SendAuthCodePort;
import com.titi.titi_auth.domain.AuthCode;
import com.titi.titi_auth.domain.TargetType;

@Service
@RequiredArgsConstructor
class GenerateAuthCodeService implements GenerateAuthCodeUseCase {

	private final PutAuthCodePort putAuthCodePort;
	private final SendAuthCodePort sendAuthCodePort;

	@Override
	public String invoke(Command command) {
		final String generatedAuthCode = GenerateAuthCodeUseCase.generateAuthCode();
		final AuthCode authCode = switch (command) {
			case Command.ToEmail cmd -> AuthCode.builder()
				.authType(cmd.authType())
				.authCode(generatedAuthCode)
				.targetType(TargetType.EMAIL)
				.targetValue(cmd.email())
				.build();
		};
		final String authKey = this.putAuthCodePort.put(authCode);
		this.sendAuthCodePort.send(authCode);
		return authKey;
	}

}
