package com.titi.titi_auth.domain.member;

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
public class ProfileImage {

	private String profileImageName;
	private String profileImageId;
	private String profileImageType;

	public static ProfileImage defaultInstance() {
		// TODO implement
		return ProfileImage.builder()
			.profileImageName(null)
			.profileImageId(null)
			.profileImageType(null)
			.build();
	}
}
