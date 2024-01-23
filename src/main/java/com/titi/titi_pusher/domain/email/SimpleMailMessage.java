package com.titi.titi_pusher.domain.email;

import lombok.Builder;

/**
 * Domain model for sending a simple email message.
 *
 * @param notificationId	The unique identifier for the notification.
 * @param from				The email address of the sender.
 * @param to				The email address of the recipient.
 * @param subject			The subject of the email.
 * @param text				The text content of the email.
 */
@Builder
public record SimpleMailMessage(
	Long notificationId,
	String from,
	String to,
	String subject,
	String text
) {

}
