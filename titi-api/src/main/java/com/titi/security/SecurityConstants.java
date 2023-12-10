package com.titi.security;

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

		public static final String[] SWAGGER_V3 = {"/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**"};

		public static List<String> getAll() {
			final List<String> whiteList = new ArrayList<>();
			whiteList.addAll(Arrays.stream(SWAGGER_V3).toList());
			return whiteList;
		}

	}

}
