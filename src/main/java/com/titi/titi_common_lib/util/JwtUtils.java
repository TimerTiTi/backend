package com.titi.titi_common_lib.util;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;

@AllArgsConstructor
public class JwtUtils {

	private static final String JWT_TYPE = "JWT";
	private final byte[] secretKey;

	public String generate(Payload payload) {
		final Payload.RegisteredClaim registeredClaim = payload.registeredClaim();
		return Jwts.builder()
			.header().type(JWT_TYPE).and()
			.issuer(registeredClaim.issuer())
			.subject(registeredClaim.subject())
			.audience().add(registeredClaim.audience()).and()
			.expiration(registeredClaim.expirationTime())
			.notBefore(registeredClaim.notBefore())
			.issuedAt(registeredClaim.issuedAt())
			.id(registeredClaim.jwtId())
			.claims(payload.additionalClaims())
			.signWith(Keys.hmacShaKeyFor(this.secretKey))
			.compact();
	}

	public Claims getPayloads(String jwt, String type) throws IllegalArgumentException {
		try {
			final Claims claims = (Claims)Jwts.parser()
				.verifyWith(Keys.hmacShaKeyFor(this.secretKey))
				.build()
				.parse(jwt)
				.getPayload();
			final String claimType = claims.get(Payload.TYPE, String.class);
			if (!claimType.equals(type)) {
				throw new IllegalArgumentException("JWT token is invalid. Type is invalid. : " + claimType);
			}
			return claims;
		} catch (JwtException e) {
			throw new IllegalArgumentException("JWT token is invalid. " + e.getMessage(), e);
		}
	}

	@Builder
	public record Payload(
		@NonNull RegisteredClaim registeredClaim,
		@Nullable Map<String, Object> additionalClaims
	) {

		public static final String TYPE = "type";

		/**
		 * Represents the registered claims in a JSON Web Token (JWT).
		 * These claims are predefined and have specific meanings in the context of JWT. <br/>
		 * See: <a href="https://tools.ietf.org/html/rfc7519#section-4.1">Registered Claim Names</a>
		 *
		 * @param issuer            Identifies the entity that issued the JWT (server-side identifier).
		 * @param subject           Identifies the subject of the JWT, which is the principal that is the subject of the JWT (client-side identifier).
		 *                			If this value is a string, it must be the client_id to authenticate.
		 * @param audience          Identifies the recipients that the JWT is intended for (client-side identifier).
		 *                 			Can be a single string or an array of strings.
		 * @param expirationTime    Expiration Time: The expiration time on or after which the JWT must not be accepted for processing.
		 *                       	It must be a numeric date representing the seconds from 1970-01-01T00:00:00Z UTC until the specified date/time.
		 * @param notBefore         Not Before: The time before which the JWT must not be accepted for processing.
		 *                  		It must be a numeric date representing the seconds from 1970-01-01T00:00:00Z UTC until the specified date/time.
		 * @param issuedAt          Issued At: The time at which the JWT was issued.
		 *                 			It must be a numeric date representing the seconds from 1970-01-01T00:00:00Z UTC until the specified date/time.
		 * @param jwtId             JWT ID: Unique identifier for the JWT.
		 *           			   	The ID should provide a unique identifier for the JWT, and it must be case-sensitive.
		 *             				It is used to prevent the JWT from being replayed.
		 */
		@Builder
		public record RegisteredClaim(
			@NonNull String issuer,
			@NonNull String subject,
			@Nullable Collection<String> audience,
			@NonNull Date expirationTime,
			@Nullable Date notBefore,
			@NonNull Date issuedAt,
			@NonNull String jwtId
		) {

		}

	}

}
