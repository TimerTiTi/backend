package com.titi.titi_auth.adapter.in.security.authentication.jwt;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

	@Serial
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
	/**
	 * Json Web Token
	 */
	private final String jwt;
	/**
	 * Member Primary Key in RDB
	 */
	private final Long id;

	private JwtAuthenticationToken(Long id, String jwt, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.id = id;
		this.jwt = jwt;
	}

	public static JwtAuthenticationToken of(Long id, String jwt, Collection<? extends GrantedAuthority> authorities) {
		return new JwtAuthenticationToken(id, jwt, authorities);
	}

	public static JwtAuthenticationToken init(String jwt) {
		return new JwtAuthenticationToken(null, jwt, new ArrayList<>());
	}

	public String getCredentials() {
		return this.jwt;
	}

	public Object getPrincipal() {
		return this.id;
	}

}
