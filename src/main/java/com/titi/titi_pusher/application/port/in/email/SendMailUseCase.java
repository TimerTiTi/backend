package com.titi.titi_pusher.application.port.in.email;

import lombok.Builder;

import com.titi.titi_pusher.domain.notification.Notification;
import com.titi.titi_pusher.domain.notification.NotificationCategory;
import com.titi.titi_pusher.domain.notification.ServiceInfo;

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
