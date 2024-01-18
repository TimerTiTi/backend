package com.titi.titi_user.data.jpa.entity;

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

import com.titi.titi_user.domain.oauth2.OAuth2Metadata;
import com.titi.infrastructure.persistence.jpa.entity.BaseEntity;

@Entity(name = "oauth2_info")
@Getter
@Builder(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2InfoEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false, updatable = false)
	private MemberEntity memberEntity;

	@Embedded
	private OAuth2Metadata metadata;

	public static OAuth2InfoEntity create(MemberEntity memberEntity, OAuth2Metadata metadata) {
		return OAuth2InfoEntity.builder()
			.memberEntity(memberEntity)
			.metadata(metadata)
			.build();
	}

}
