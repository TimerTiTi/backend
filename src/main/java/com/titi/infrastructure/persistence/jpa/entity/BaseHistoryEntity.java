package com.titi.infrastructure.persistence.jpa.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseHistoryEntity {

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;
	@PreUpdate
	private void preUpdate() {
		throw new UnsupportedOperationException();
	}

}
