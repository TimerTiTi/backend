package com.titi.titi_auth.domain;

import lombok.Builder;

@Builder
public record Account(
	Long id,
	String username,
	String password,
	Authority authority,
	AccountStatus accountStatus
) {

}
