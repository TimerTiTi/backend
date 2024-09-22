package com.titi.titi_pusher.application.port.in.email;

import lombok.Builder;

public interface SendMailUseCase {

	Result invoke(Command command);

	sealed interface Command {

		@Builder
		record ToSimpleMessage(
			String from,
			String to,
			String subject,
			String text,
			String notificationCategory,
			String serviceName,
			String serviceRequestId
		) implements Command {

		}

	}

	@Builder
	record Result(
		String messageId,
		String messageStatus
	) {

	}

}
