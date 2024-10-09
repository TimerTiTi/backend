package com.titi.titi_user.data.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.titi.infrastructure.persistence.jpa.entity.BaseEntity;
import com.titi.titi_user.domain.member.MembershipType;
import com.titi.titi_user.domain.member.ProfileImage;

@Entity(name = "members")
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, updatable = false)
	private Long accountId;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false, insertable = false, updatable = false)
	private String hashcode;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	private MembershipType membershipType;

	@Embedded
	private ProfileImage profileImage;

}
