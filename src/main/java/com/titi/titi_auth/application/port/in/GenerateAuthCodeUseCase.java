package com.titi.titi_auth.application.port.in;

import static com.titi.titi_common_lib.constant.Constants.*;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.Builder;

import com.titi.titi_auth.adapter.out.cache.AuthCacheKeys;
import com.titi.titi_auth.domain.AuthCode;
import com.titi.titi_auth.domain.AuthenticationType;
import com.titi.titi_common_lib.util.RandomGenerator;
import com.titi.titi_crypto_lib.util.HashingUtils;

@Validated
public interface GenerateAuthCodeUseCase {

	int AUTH_CODE_LENGTH = 6;

	static String generateAuthKey(AuthCode authCode) {
		return HashingUtils.hashSha256(AuthCacheKeys.AUTH_CODE.getPrefix(), UNDERSCORE,
			authCode.authType().getShortenName(), authCode.targetType().getShortenName(), authCode.targetValue()
		);
	}

	static String generateAuthCode() {
		return RandomGenerator.generate(true, true, true, AUTH_CODE_LENGTH, false);
	}

	Result invoke(@Valid Command command);

	sealed interface Command {

		@Builder
		record ToEmail(
			@Email String email,
			AuthenticationType authType
		) implements Command {

		}

	}

	@Builder
	record Result(
		String authKey
	) {

	}

}
