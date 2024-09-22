package com.titi.titi_pusher.application.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import com.titi.titi_pusher.application.port.in.email.SendMailUseCase;
import com.titi.titi_pusher.application.port.out.persistence.notification.CreateNotificationPort;
import com.titi.titi_pusher.application.port.out.persistence.notification.UpdateNotificationPort;
import com.titi.titi_pusher.application.port.out.smtp.SendMailPort;
import com.titi.titi_pusher.domain.email.SimpleMailMessage;
import com.titi.titi_pusher.domain.notification.Notification;
import com.titi.titi_pusher.domain.notification.NotificationCategory;
import com.titi.titi_pusher.domain.notification.ServiceInfo;

@Service
@RequiredArgsConstructor
class SendMailService implements SendMailUseCase {

	private final SendMailPort sendMailPort;
	private final CreateNotificationPort createNotificationPort;
	private final UpdateNotificationPort updateNotificationPort;

	@Override
	@Transactional
	public Result invoke(Command command) {
		return switch (command) {
			case Command.ToSimpleMessage message -> {
				Notification notification = this.createNotification(message);
				final boolean isSuccessful = this.sendNotification(notification, message);
				notification = this.finishNotification(notification, isSuccessful);
				yield Result.builder()
					.messageId(notification.notificationId().toString())
					.messageStatus(notification.notificationStatus().name())
					.build();
			}
		};
	}

	private Notification createNotification(Command.ToSimpleMessage message) {
		final Notification notification = Notification.ofEmail(
			NotificationCategory.valueOf(message.notificationCategory()),
			message.to(),
			ServiceInfo.builder()
				.name(ServiceInfo.ServiceName.valueOf(message.serviceName()))
				.requestId(message.serviceRequestId())
				.build()
		);
		this.createNotificationPort.create(notification);
		return notification;
	}

	private boolean sendNotification(Notification notification, Command.ToSimpleMessage message) {
		return this.sendMailPort.sendSimpleMessage(
			SimpleMailMessage.builder()
				.notificationId(notification.notificationId())
				.from(message.from())
				.to(message.to())
				.subject(message.subject())
				.text(message.text())
				.build()
		);
	}

	private Notification finishNotification(Notification notification, boolean isSuccessful) {
		notification = isSuccessful ? notification.complete() : notification.fail();
		this.updateNotificationPort.update(notification);
		return notification;
	}

}
