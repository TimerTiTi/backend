package com.titi.titi_user.domain.oauth2;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class OAuth2Metadata {

	@Column(nullable = false, updatable = false)
	private String providerId;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false, updatable = false)
	private OAuth2Provider provider;

	@Column(nullable = false)
	private String accessToken;

	@Column(nullable = false)
	private String refreshToken;

}
