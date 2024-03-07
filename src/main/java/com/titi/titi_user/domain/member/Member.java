package com.titi.titi_user.domain.member;

import lombok.Builder;

@Builder
public record Member(
	Long id,
	String username,
	String password,
	String nickname,
	String hashcode,
	Authority authority,
	AccountStatus accountStatus,
	MembershipType membershipType,
	ProfileImage profileImage
) {

}
