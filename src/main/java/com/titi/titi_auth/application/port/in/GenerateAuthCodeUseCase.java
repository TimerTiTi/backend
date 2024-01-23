package com.titi.titi_auth.application.port.in;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.Builder;

import com.titi.titi_auth.domain.AuthenticationType;
import com.titi.titi_common_lib.util.RandomGenerator;

@Validated
public interface GenerateAuthCodeUseCase {

	int AUTH_CODE_LENGTH = 6;

	static String generateAuthCode() {
		return RandomGenerator.generate(true, true, true, AUTH_CODE_LENGTH, false);
	}

	boolean invoke(@Valid Command command);

	sealed interface Command {

		@Builder
		record ToEmail(
			@Email String email,
			AuthenticationType authType
		) implements Command {

		}

	}

}
