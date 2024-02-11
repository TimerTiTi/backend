package com.titi.security.authentication.jwt;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

import com.titi.security.authentication.exception.TiTiAuthenticationException;
import com.titi.security.constant.SecurityConstants;
import com.titi.titi_common_lib.dto.ErrorResponse.FieldError;
import com.titi.titi_common_lib.util.JwtUtils;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

	private static final String CLAIM_AUTHORITIES = "aut";
	private final JwtUtils jwtUtils;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		final String jwt = ((JwtAuthenticationToken)authentication).getCredentials();
		try {
			final Claims claims = jwtUtils.getPayloads(jwt);
			this.checkTokenRevocation(claims.getId());
			final Long memberId = Long.valueOf(claims.getSubject());
			return JwtAuthenticationToken.of(memberId, jwt, getAuthorities(claims));
		} catch (IllegalArgumentException e) {
			final List<FieldError> errors = FieldError.of(SecurityConstants.REQUEST_HEADER_AUTHORIZATION, jwt, e.getMessage());
			throw new TiTiAuthenticationException(errors);
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}

	@SuppressWarnings("unchecked")
	private List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
		final List<String> authorities = (List<String>)claims.getOrDefault(CLAIM_AUTHORITIES, List.of());
		return authorities.stream()
			.map(SimpleGrantedAuthority::new)
			.collect(Collectors.toList());
	}

	// TODO - implement
	private void checkTokenRevocation(String jti) {
		// If the corresponding jti of Jwt exists in the revoked token list in Redis, it will throw an TiTiAuthenticationException.
	}

}
