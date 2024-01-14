package com.titi.auth.adapter.out.persistence.jpa.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.titi.auth.domain.member.AccountStatus;
import com.titi.auth.domain.member.Authority;
import com.titi.auth.domain.member.MembershipType;
import com.titi.auth.domain.member.ProfileImage;
import com.titi.infrastructure.persistence.jpa.entity.BaseEntity;

@Entity(name = "members")
@Getter
@Builder(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;
	private String nickname;
	private String hashcode;
	private Authority authority;
	private AccountStatus accountStatus;
	private MembershipType membershipType;
	@Embedded
	private ProfileImage profileImage;
	private String refreshToken;

	public static Member create(String username, String encryptedPassword, String nickname, String refreshToken) {
		return Member.builder()
			.username(username)
			.password(encryptedPassword)
			.nickname(nickname)
			.authority(Authority.MEMBER)
			.accountStatus(AccountStatus.ACTIVATED)
			.membershipType(MembershipType.NORMAL)
			.profileImage(ProfileImage.defaultInstance())
			.refreshToken(refreshToken)
			.build();
	}

}
