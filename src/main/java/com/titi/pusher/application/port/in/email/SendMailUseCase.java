package com.titi.pusher.application.port.in.email;

import lombok.Builder;

import com.titi.pusher.domain.notification.Notification;
import com.titi.pusher.domain.notification.NotificationCategory;
import com.titi.pusher.domain.notification.ServiceInfo;

public interface SendMailUseCase {

	Notification invoke(Command command);

	sealed interface Command {

		@Builder
		record ToSimpleMessage(
			String from,
			String to,
			String subject,
			String text,
			NotificationCategory notificationCategory,
			ServiceInfo.ServiceName serviceName,
			String serviceRequestId
		) implements Command {

		}

	}

}
