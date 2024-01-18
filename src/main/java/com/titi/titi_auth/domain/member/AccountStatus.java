package com.titi.titi_auth.domain.member;

public enum AccountStatus {
	/**
	 * The account is in a normal and usable state. <br/>
	 * The account owner can log in and access the application.
	 */
	ACTIVATED,
	/**
	 * An account exists but is in an unusable state. <br/>
	 * This could be a temporary condition, or it might occur when the account owner deactivates the account without deleting it. <br/>
	 * Inactive accounts are typically eligible for reactivation.
	 */
	DEACTIVATED,
	/**
	 * The account is temporarily suspended. <br/>
	 * This can occur due to security issues, payment problems, or transitions to a dormant state. <br/>
	 * In this state, the account owner cannot access the application.
	 */
	SUSPENDED,
	/**
	 * The account is permanently suspended. <br/>
	 * This can occur due to violations of the terms of service, among other reasons. <br/>
	 * In this state, the account owner cannot access the application.
	 */
	BLOCKED,
	/**
	 * The account has been permanently deleted. <br/>
	 * This typically occurs when the account owner initiates the deletion or when an administrator removes the account. <br/>
	 * Deleted accounts cannot be reactivated.
	 */
	DELETED
}
