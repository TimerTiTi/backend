package com.titi.titi_user.domain.device;

import java.time.LocalDateTime;

import lombok.Builder;

import com.titi.titi_user.domain.member.Member;

@Builder(toBuilder = true)
public record Device(
	String deviceId,
	Member member,
	String deviceType,
	LocalDateTime lastAccessedAt
) {

}
