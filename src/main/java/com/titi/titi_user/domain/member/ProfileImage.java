package com.titi.titi_user.domain.member;

import java.util.UUID;

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
public class ProfileImage {

	@Column(nullable = false)
	private String profileImageName;

	@Column(nullable = false)
	private String profileImageId;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	private ProfileImageType profileImageType;

	public static ProfileImage defaultInstance() {
		// TODO implement
		return ProfileImage.builder()
			.profileImageName("name")
			.profileImageId(UUID.randomUUID().toString())
			.profileImageType(ProfileImageType.PNG)
			.build();
	}
}
