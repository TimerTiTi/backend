package com.titi.auth.adapter.out.persistence.jpa.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.titi.auth.domain.oauth2.OAuth2Metadata;
import com.titi.infrastructure.persistence.jpa.entity.BaseEntity;

@Entity(name = "oauth2_info")
@Getter
@Builder(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2Info extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	@Embedded
	private OAuth2Metadata metadata;

	public static OAuth2Info create(Member member, OAuth2Metadata metadata) {
		return OAuth2Info.builder()
			.member(member)
			.metadata(metadata)
			.build();
	}

}
