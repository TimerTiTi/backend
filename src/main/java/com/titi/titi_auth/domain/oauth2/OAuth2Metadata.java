package com.titi.titi_auth.domain.oauth2;

import jakarta.persistence.Embeddable;
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

	private String providerId;
	private OAuth2Provider provider;
	private String accessToken;
	private String refreshToken;

}
