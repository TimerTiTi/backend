package com.titi.titi_user.data.jpa.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.titi.infrastructure.persistence.jpa.entity.BaseEntity;

@Entity(name = "devices")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeviceEntity extends BaseEntity {

	@Id
	@UuidGenerator(style = UuidGenerator.Style.RANDOM)
	private UUID uuid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private MemberEntity member;

	private String deviceType;

	@Column(nullable = false)
	private LocalDateTime lastAccessedAt;

	public void updateLastAccessedAt(LocalDateTime lastAccessedAt) {
		this.lastAccessedAt = lastAccessedAt;
	}

}
