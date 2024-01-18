package com.titi.titi_pusher.adapter.in.internal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

import com.titi.titi_pusher.application.port.in.email.SendMailUseCase;
import com.titi.titi_pusher.domain.notification.Notification;

@Component
@RequiredArgsConstructor
public class InternalSendMailGateway {

	private final SendMailUseCase sendMailUseCase;
	/**
	 * The official email account of the Timer TiTi service.
	 */
	@Value("${spring.mail.username}")
	private String from;

	public SendSimpleMessageResponse sendSimpleMessage(SendSimpleMessageRequest request) {
		final Notification notification = this.sendMailUseCase.invoke(
			SendMailUseCase.Command.ToSimpleMessage.builder()
				.from(this.from)
				.to(request.to())
				.subject(request.subject())
				.text(request.text())
				.build()
		);
		return SendSimpleMessageResponse.builder()
			.messageId(notification.notificationId().toString())
			.messageStatus(notification.notificationStatus().name())
			.build();
	}

	@Builder
	public record SendSimpleMessageRequest(
		String to,
		String subject,
		String text
	) {

	}

	@Builder
	public record SendSimpleMessageResponse(
		String messageId,
		String messageStatus
	) {

	}

}
