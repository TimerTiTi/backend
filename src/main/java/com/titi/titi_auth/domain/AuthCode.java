package com.titi.titi_auth.domain;

import lombok.Builder;

@Builder
public record AuthCode(
	AuthenticationType authType,
	String authCode,
	TargetType targetType,
	String targetValue
) {

}
