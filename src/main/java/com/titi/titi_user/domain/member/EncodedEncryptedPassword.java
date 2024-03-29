package com.titi.titi_user.domain.member;

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

@Slf4j
@Builder
public record EncodedEncryptedPassword(
	String value
) {

	private static final String RAW_PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[~!@#$%^&()])[A-Za-z\\d~!@#$%^&()]{10,20}$";
	private static final Pattern PATTERN = Pattern.compile(RAW_PASSWORD_REGEX);

	public String getRawPassword(byte[] secretKey) {
		final byte[] encryptedPassword = this.decodePassword();
		return this.decryptPassword(secretKey, encryptedPassword);
	}

	private String decryptPassword(byte[] secretKey, byte[] encryptedPassword) {
		try {
			final String rawPassword = new String(AESUtils.decrypt(secretKey, encryptedPassword, AESCipherModes.GCM_NO_PADDING), StandardCharsets.UTF_8);
			this.validateRawPasswordPattern(rawPassword);
			return rawPassword;
		} catch (TiTiCryptoException e) {
			log.error("Decrypt the encodedEncryptedPassword failed. ", e);
			final List<ErrorResponse.FieldError> errors = ErrorResponse.FieldError.of("encodedEncryptedPassword", this.value, "Decrypt the encodedEncryptedPassword failed.");
			throw new TiTiException(TiTiErrorCodes.INPUT_TYPE_INVALID, errors);
		}
	}

	private byte[] decodePassword() {
		try {
			return Base64.getUrlDecoder().decode(this.value);
		} catch (IllegalArgumentException e) {
			log.error("Base64url decoding the encodedEncryptedPassword failed. ", e);
			final List<ErrorResponse.FieldError> errors = ErrorResponse.FieldError.of("encodedEncryptedPassword", this.value,
				"Base64url decoding the encodedEncryptedPassword failed.");
			throw new TiTiException(TiTiErrorCodes.INPUT_TYPE_INVALID, errors);
		}
	}

	private void validateRawPasswordPattern(String rawPassword) {
		if (!PATTERN.matcher(rawPassword).matches()) {
			final List<ErrorResponse.FieldError> errors = ErrorResponse.FieldError.of("encodedEncryptedPassword", this.value,
				"The rawPassword before being encrypted into encodedEncryptedPassword must match " + RAW_PASSWORD_REGEX);
			throw new TiTiException(TiTiErrorCodes.INPUT_TYPE_INVALID, errors);
		}
	}

}
