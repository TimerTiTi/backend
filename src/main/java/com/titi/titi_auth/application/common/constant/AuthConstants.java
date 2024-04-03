package com.titi.titi_auth.application.common.constant;

import static com.titi.titi_common_lib.constant.Constants.*;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthConstants {

	public static final String SERVICE_NAME = "AUTH";
	public static final String JWT_ISSUER = "TiTi-Auth";
	public static final String AUTH_TOKEN = "authToken";
	public static final String ACCESS_TOKEN = "accessToken";
	public static final String REFRESH_TOKEN = "refreshToken";
	/**
	 * 30 minutes
	 */
	public static final int ACCESS_TOKEN_EXPIRATION_TIME = 30 * 60 * SECONDS;
	/**
	 * 2 weeks
	 */
	public static final int REFRESH_TOKEN_EXPIRATION_TIME = 14 * 24 * 60 * 60 * SECONDS;

}
