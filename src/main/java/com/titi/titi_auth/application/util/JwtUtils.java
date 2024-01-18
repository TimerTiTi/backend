package com.titi.titi_auth.application.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

	public static final String TOKEN_TYPE = "Bearer";
	public static final String CLAIM_AUTHORITIES = "aut";
	private final String secretKey;

	public JwtUtils(@Value("${jwt.secret-key}") String secretKey) {
		this.secretKey = secretKey;
	}

	public Claims getPayloads(String jwt) throws JwtException {
		return (Claims)Jwts.parser()
			.verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
			.build()
			.parse(jwt)
			.getPayload();
	}

}
