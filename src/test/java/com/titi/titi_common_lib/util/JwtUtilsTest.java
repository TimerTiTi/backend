package com.titi.titi_common_lib.util;

import static org.assertj.core.api.Assertions.*;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

class JwtUtilsTest {

	private final static String CLAIM_KEY = "testKey";
	private final static String CLAIM_VALUE = "testValue";
	private final static String ISSUER = "issuer";
	private final static String SUBJECT = "subject";
	private final static String JWT_ID = "jwtId";
	public static final Instant NOW = Instant.parse("2024-02-11T10:00:00.000000000Z");
	private final static Date EXPIRED_DATE = Date.from(NOW.minus(1, TimeUnit.DAYS.toChronoUnit()));
	private final static Date EXPIRATION_DATE = Date.from(NOW.plus(99999999, TimeUnit.DAYS.toChronoUnit()));
	private final static Date ISSUED_DATE = Date.from(NOW);
	private static final JwtUtils.Payload PAYLOAD = JwtUtils.Payload.builder()
		.registeredClaim(
			JwtUtils.Payload.RegisteredClaim.builder()
				.issuer(ISSUER)
				.subject(SUBJECT)
				.expirationTime(EXPIRATION_DATE)
				.issuedAt(ISSUED_DATE)
				.jwtId(JWT_ID)
				.build()
		)
		.additionalClaims(Map.of(CLAIM_KEY, CLAIM_VALUE))
		.build();
	private final static String SECRET_KEY = "abcdefghijklmnopqrstuwxzy0123456";
	private static final JwtBuilder JWT_BUILDER = Jwts.builder()
		.claim(CLAIM_KEY, CLAIM_VALUE)
		.issuer(ISSUER)
		.subject(SUBJECT)
		.issuedAt(ISSUED_DATE)
		.id(JWT_ID)
		.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()));
	private final JwtUtils jwtUtils = new JwtUtils(SECRET_KEY.getBytes());

	@Test
	void generateAndGetPayLoadsScenario() {
		final String jwt = jwtUtils.generate(PAYLOAD);
		final Claims claims = jwtUtils.getPayloads(jwt);
		assertThat(claims.getIssuer()).isEqualTo(ISSUER);
		assertThat(claims.getSubject()).isEqualTo(SUBJECT);
		assertThat(claims.getExpiration()).isEqualTo(EXPIRATION_DATE);
		assertThat(claims.getIssuedAt()).isEqualTo(ISSUED_DATE);
		assertThat(claims.getId()).isEqualTo(JWT_ID);
		assertThat(claims.get(CLAIM_KEY)).isEqualTo(CLAIM_VALUE);
	}

	@Nested
	class Generate {

		@Test
		void givenValidPeriodThenSuccessful() {
			assertThat(jwtUtils.generate(PAYLOAD)).isNotNull();
		}

	}

	@Nested
	class GetPayLoads {

		@Test
		void givenValidJwtThenSuccessful() {
			assertThat(jwtUtils.getPayloads(JWT_BUILDER.expiration(EXPIRATION_DATE).compact()).get(CLAIM_KEY)).isEqualTo(CLAIM_VALUE);
		}

		@Test
		void givenExpiredJwtThenThrowIllegalArgumentException() {
			assertThatCode(() -> jwtUtils.getPayloads(JWT_BUILDER.expiration(EXPIRED_DATE).compact())).isInstanceOf(IllegalArgumentException.class);
		}

	}

}
