package com.titi.security.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityConstants {

	public static final String REQUEST_HEADER_AUTHORIZATION = "Authorization";

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class AuthenticationWhiteList {

		public static final String[] SWAGGER_V3 = {"/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**", "/swagger-ui.html"};
		public static final String[] AUTH_API = {"/api/auth/**"};
		public static final String[] USER_API = {"/api/user/members/check", "/api/user/members/register"};

		public static List<String> getAllPatterns() {
			final List<String> whiteList = new ArrayList<>();
			whiteList.addAll(Arrays.stream(SWAGGER_V3).toList());
			whiteList.addAll(Arrays.stream(AUTH_API).toList());
			whiteList.addAll(Arrays.stream(USER_API).toList());
			return whiteList;
		}

	}

}
