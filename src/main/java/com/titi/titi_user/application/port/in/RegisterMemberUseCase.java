package com.titi.titi_user.application.port.in;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import com.titi.exception.TiTiErrorCodes;
import com.titi.exception.TiTiException;
import com.titi.titi_common_lib.dto.ErrorResponse;
import com.titi.titi_crypto_lib.constant.AESCipherModes;
import com.titi.titi_crypto_lib.exception.TiTiCryptoException;
import com.titi.titi_crypto_lib.util.AESUtils;
import com.titi.titi_crypto_lib.util.HashingUtils;
import com.titi.titi_user.common.TiTiUserBusinessCodes;
import com.titi.titi_user.common.TiTiUserException;

public interface RegisterMemberUseCase {

	void invoke(Command command);

	@Slf4j
	@Builder
	record Command(
		String username,
		String encodedWrappedPassword,
		String nickname,
		String authToken
	) {

		private static final String RAW_PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[~!@#$%^&()])[A-Za-z\\d~!@#$%^&()]{10,20}$";
		private static final Pattern PATTERN = Pattern.compile(RAW_PASSWORD_REGEX);
		private static final String AUTH_KEY_PREFIX = "ac_SU_E";

		public String unwrapPassword(byte[] wrappingKey) {
			final byte[] wrappedPassword = this.decodePassword();
			return this.unwrapPassword(wrappingKey, wrappedPassword);
		}

		public void validateAuthKey(String authKey) {
			if (!authKey.equals(HashingUtils.hashSha256(AUTH_KEY_PREFIX, this.username))) {
				throw new TiTiUserException(TiTiUserBusinessCodes.AUTH_KEY_MISMATCHED_REGISTRATION_INFORMATION);
			}
		}

		private String unwrapPassword(byte[] wrappingKey, byte[] wrappedPassword) {
			try {
				final String rawPassword = new String(AESUtils.decrypt(wrappingKey, wrappedPassword, AESCipherModes.GCM_NO_PADDING), StandardCharsets.UTF_8);
				this.validateRawPasswordPattern(rawPassword);
				return rawPassword;
			} catch (TiTiCryptoException e) {
				log.error("Unwrapping the encodedWrappedPassword failed. ", e);
				final List<ErrorResponse.FieldError> errors = ErrorResponse.FieldError.of("encodedWrappedPassword", this.encodedWrappedPassword, "Unwrapping the encodedWrappedPassword failed.");
				throw new TiTiException(TiTiErrorCodes.INPUT_TYPE_INVALID, errors);
			}
		}

		private byte[] decodePassword() {
			try {
				return Base64.getUrlDecoder().decode(this.encodedWrappedPassword);
			} catch (IllegalArgumentException e) {
				log.error("Base64url decoding the encodedWrappedPassword failed. ", e);
				final List<ErrorResponse.FieldError> errors = ErrorResponse.FieldError.of("encodedWrappedPassword", this.encodedWrappedPassword, "Base64url decoding the encodedWrappedPassword failed.");
				throw new TiTiException(TiTiErrorCodes.INPUT_TYPE_INVALID, errors);
			}
		}

		private void validateRawPasswordPattern(String rawPassword) {
			if (!PATTERN.matcher(rawPassword).matches()) {
				final List<ErrorResponse.FieldError> errors = ErrorResponse.FieldError.of("encodedWrappedPassword", this.encodedWrappedPassword,
					"The rawPassword before being wrapped into encodedWrappedPassword must match " + RAW_PASSWORD_REGEX);
				throw new TiTiException(TiTiErrorCodes.INPUT_TYPE_INVALID, errors);
			}
		}

	}

}
