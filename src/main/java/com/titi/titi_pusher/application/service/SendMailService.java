package com.titi.titi_pusher.application.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.titi.titi_pusher.application.port.in.email.SendMailUseCase;
import com.titi.titi_pusher.application.port.out.persistence.notification.CreateNotificationPort;
import com.titi.titi_pusher.application.port.out.persistence.notification.UpdateNotificationPort;
import com.titi.titi_pusher.application.port.out.smtp.SendMailPort;
import com.titi.titi_pusher.domain.email.SimpleMailMessage;
import com.titi.titi_pusher.domain.notification.Notification;
import com.titi.titi_pusher.domain.notification.ServiceInfo;

@Slf4j
@Service
@RequiredArgsConstructor
class SendMailService implements SendMailUseCase {

	private final SendMailPort sendMailPort;
	private final CreateNotificationPort createNotificationPort;
	private final UpdateNotificationPort updateNotificationPort;

	@Override
	@Transactional
	public Notification invoke(Command command) {
		return switch (command) {
			case Command.ToSimpleMessage message -> {
				final Notification notification = this.createNotification(message);
				final boolean isSuccessful = this.sendNotification(message);
				log.info("[SendMailUseCase] {} to send mail. notificationId: {}", isSuccessful ? "Success" : "Fail", notification.notificationId());
				yield this.finishNotification(notification, isSuccessful);
			}
		};
	}

	private Notification createNotification(Command.ToSimpleMessage message) {
		Notification notification = Notification.ofEmail(
			message.notificationCategory(),
			message.to(),
			ServiceInfo.builder()
				.name(message.serviceName())
				.requestId(message.serviceRequestId())
				.build()
		);
		this.createNotificationPort.create(notification);
		return notification;
	}

	private boolean sendNotification(Command.ToSimpleMessage message) {
		return this.sendMailPort.sendSimpleMessage(
			SimpleMailMessage.builder()
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
