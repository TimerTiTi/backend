package com.titi.titi_auth.data.jpa.entity;

import jakarta.persistence.Column;
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
import com.titi.titi_auth.domain.AccountStatus;
import com.titi.titi_auth.domain.Authority;

@Entity(name = "accounts")
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	private Authority authority;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	private AccountStatus accountStatus;

}
