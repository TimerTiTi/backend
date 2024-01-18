package com.titi.titi_auth.application.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

class JwtUtilsTest {

	// @Autowired
	private final static String SECRET_KEY = "abcdefghijklmnopqrstuwxzy0123456";
	private final JwtUtils jwtUtils = new JwtUtils(SECRET_KEY);

	@Test
	void testGetPayLoads() {
		// given
		final String claimKey = "testKey";
		final String claimValue = "testValue";
		final String jwt = Jwts.builder()
			.claim(claimKey, claimValue)
			.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
			.compact();

		// when
		final Claims claims = jwtUtils.getPayloads(jwt);

		// then
		assertThat(claims.get(claimKey)).isEqualTo(claimValue);
	}

}
