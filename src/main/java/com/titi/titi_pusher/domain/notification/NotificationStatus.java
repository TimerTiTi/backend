package com.titi.titi_pusher.domain.notification;

public enum NotificationStatus {
	/**
	 * Notification entity was just created.
	 */
	CREATED,
	/**
	 * Notification delivery completed successfully.
	 */
	COMPLETED,
	/**
	 * Notification delivery failed.
	 */
	FAILED,
}
