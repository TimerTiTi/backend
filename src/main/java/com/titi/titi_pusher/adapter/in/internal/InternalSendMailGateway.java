package com.titi.titi_pusher.adapter.in.internal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import com.titi.titi_pusher.adapter.in.internal.dto.SendMessageResponse;
import com.titi.titi_pusher.application.port.in.email.SendMailUseCase;
import com.titi.titi_pusher.domain.notification.Notification;
import com.titi.titi_pusher.domain.notification.NotificationCategory;
import com.titi.titi_pusher.domain.notification.ServiceInfo;

@Validated
@Component
@RequiredArgsConstructor
public class InternalSendMailGateway {

	private final SendMailUseCase sendMailUseCase;
	/**
	 * The official email account of the Timer TiTi service.
	 */
	@Value("${spring.mail.username}")
	private String from;

	public SendMessageResponse sendSimpleMessage(@Valid SendSimpleMessageRequest request) {
		final Notification notification = this.sendMailUseCase.invoke(
			SendMailUseCase.Command.ToSimpleMessage.builder()
				.from(this.from)
				.to(request.to())
				.subject(request.subject())
				.text(request.text())
				.notificationCategory(NotificationCategory.valueOf(request.notificationCategory))
				.serviceName(ServiceInfo.ServiceName.valueOf(request.serviceName))
				.serviceRequestId(request.serviceRequestId)
				.build()
		);
		return SendMessageResponse.builder()
			.messageId(notification.notificationId().toString())
			.messageStatus(notification.notificationStatus().name())
			.build();
	}

	@Builder
	public record SendSimpleMessageRequest(
		@NotBlank String to,
		@NotBlank String subject,
		@NotBlank String text,
		@NotBlank String notificationCategory,
		@NotBlank String serviceName,
		@NotBlank String serviceRequestId
	) {

	}

}
