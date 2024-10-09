package com.titi.titi_user.application.port.in;

import lombok.Builder;

import com.titi.titi_crypto_lib.util.HashingUtils;
import com.titi.titi_user.common.TiTiUserBusinessCodes;
import com.titi.titi_user.common.TiTiUserException;

public interface RegisterMemberUseCase {

	void invoke(Command command);

	@Builder
	record Command(
		String username,
		String encodedEncryptedPassword,
		String nickname,
		String authToken
	) {

		private static final String AUTH_KEY_PREFIX = "ac_SU_E";

		public void validateAuthKey(String authKey) {
			if (!authKey.equals(HashingUtils.hashSha256(AUTH_KEY_PREFIX, this.username))) {
				throw new TiTiUserException(TiTiUserBusinessCodes.AUTH_KEY_MISMATCHED_REGISTRATION_INFORMATION);
			}
		}

	}

}
