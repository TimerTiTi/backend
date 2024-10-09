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

import com.titi.infrastructure.persistence.jpa.entity.BaseEntity;
import com.titi.titi_auth.data.jpa.entity.AccountEntity;
import com.titi.titi_user.domain.oauth2.OAuth2Metadata;

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
	@JoinColumn(name = "account_id", nullable = false, updatable = false)
	private AccountEntity accountEntity;

	@Embedded
	private OAuth2Metadata metadata;

	public static OAuth2InfoEntity create(AccountEntity accountEntity, OAuth2Metadata metadata) {
		return OAuth2InfoEntity.builder()
			.accountEntity(accountEntity)
			.metadata(metadata)
			.build();
	}

}
