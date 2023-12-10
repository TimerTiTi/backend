package com.titi.util;

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

	@Value("${jwt.secret-key}")
	private String SECRET_KEY;

	public Claims getPayloads(String jwt) throws JwtException {
		return (Claims)Jwts.parser()
			.verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
			.build()
			.parse(jwt)
			.getPayload();
	}

}
