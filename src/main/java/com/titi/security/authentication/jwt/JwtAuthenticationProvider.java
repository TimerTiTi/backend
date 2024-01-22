package com.titi.security.authentication.jwt;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

import com.titi.security.authentication.exception.TiTiAuthenticationException;
import com.titi.security.constant.SecurityConstants;
import com.titi.titi_common_lib.util.JwtUtils;
import com.titi.titi_common_lib.dto.ErrorResponse.FieldError;

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
			final List<FieldError> errors = FieldError.of(SecurityConstants.REQUEST_HEADER_AUTHORIZATION, jwt, e.getMessage());
			throw new TiTiAuthenticationException(errors);
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
			.collect(Collectors.toList());
	}

	// TODO - implement
	private void checkTokenRevocation(String jti) {
		// If the corresponding jti of Jwt exists in the revoked token list in Redis, it will throw an TiTiAuthenticationException.
	}

}