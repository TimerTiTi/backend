package com.titi.auth.adapter.in.security.authentication.jwt;

import static com.titi.common.constant.ResponseCodes.ErrorCode.*;
import static java.util.stream.Collectors.*;

import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

import com.titi.auth.adapter.in.security.authentication.TiTiAuthenticationException;
import com.titi.auth.adapter.in.security.constant.SecurityConstants;
import com.titi.common.dto.ErrorResponse;
import com.titi.auth.application.util.JwtUtils;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

	private final JwtUtils jwtUtils;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		final String jwt = ((JwtAuthenticationToken)authentication).getCredentials();
		try {
			final Claims payloads = jwtUtils.getPayloads(jwt);
			this.checkTokenRevocation(payloads.getId());
			final Long memberId = Long.valueOf(payloads.getSubject());
			return JwtAuthenticationToken.of(memberId, jwt, getAuthorities(payloads));
		} catch (JwtException e) {
			final List<ErrorResponse.FieldError> errors = ErrorResponse.FieldError.of(SecurityConstants.REQUEST_HEADER_AUTHORIZATION, jwt, e.getMessage());
			throw new TiTiAuthenticationException(AUTHENTICATION_FAILURE, errors);
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}

	@SuppressWarnings("unchecked")
	private List<SimpleGrantedAuthority> getAuthorities(Claims payloads) {
		final List<String> authorities = (List<String>)payloads.get(JwtUtils.CLAIM_AUTHORITIES);
		return authorities.stream()
			.map(SimpleGrantedAuthority::new)
			.collect(toList());
	}

	// TODO - implement
	private void checkTokenRevocation(String jti) {
		// If the corresponding jti of Jwt exists in the revoked token list in Redis, it will throw an TiTiAuthenticationException.
	}

}