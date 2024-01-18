package com.titi.titi_common_lib.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtUtils {

	public static final String TOKEN_TYPE = "Bearer";
	public static final String CLAIM_AUTHORITIES = "aut";
	private final String secretKey;

	public Claims getPayloads(String jwt) throws JwtException {
		return (Claims)Jwts.parser()
			.verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
			.build()
			.parse(jwt)
			.getPayload();
	}

}
