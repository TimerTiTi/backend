package com.titi.auth.application.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@SpringBootTest
class JwtUtilsTest {

	@Autowired
	private JwtUtils jwtUtils;
	private final static String SECRET_KEY = "abcdefghijklmnopqrstuwxzy0123456";

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
