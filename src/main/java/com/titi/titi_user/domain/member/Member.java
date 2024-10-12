package com.titi.titi_user.domain.member;

import lombok.Builder;

@Builder
public record Member(
	Long id,
	Long accountId,
	String nickname,
	String hashcode,
	MembershipType membershipType,
	ProfileImage profileImage
) {

}
