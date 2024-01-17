package com.titi.pusher.domain.notification;

import lombok.Builder;

@Builder
public record ServiceInfo(
	ServiceName name,
	String requestId
) {

	public enum ServiceName {
		/**
		 * Authentication service module.
		 */
		AUTH
	}

}
