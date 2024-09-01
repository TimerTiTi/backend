package com.titi.titi_auth.adapter.out.pusher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.titi.titi_auth.application.common.constant.AuthConstants;
import com.titi.titi_auth.application.port.out.pusher.SendAuthCodePort;
import com.titi.titi_auth.common.TiTiAuthBusinessCodes;
import com.titi.titi_auth.common.TiTiAuthException;
import com.titi.titi_auth.domain.AuthCode;
import com.titi.titi_auth.domain.TargetType;
import com.titi.titi_crypto_lib.util.HashingUtils;
import com.titi.titi_pusher.application.port.in.email.SendMailUseCase;

@Slf4j
@Component
@RequiredArgsConstructor
class SendAuthCodePortAdapter implements SendAuthCodePort {

	private final SendMailUseCase sendMailUseCase;
	/**
	 * The official email account of the Timer TiTi service.
	 */
	@Value("${spring.mail.username}")
	private String from;

	@Override
	public void invoke(AuthCode authCode) {
		final String requestId = Logger.makeRequestId(authCode);
		final SendMailUseCase.Result response = switch (authCode.targetType()) {
			case TargetType.EMAIL -> this.sendMailUseCase.invoke(
				SendMailUseCase.Command.ToSimpleMessage.builder()
					.from(this.from)
					.to(authCode.targetValue())
					.subject(AuthMessageTemplates.GENERATE_AUTH_CODE.getSubject())
					.text(String.format(AuthMessageTemplates.GENERATE_AUTH_CODE.getText(), authCode.authCode()))
					.notificationCategory(SendAuthCodePort.NOTIFICATION_CATEGORY)
					.serviceName(AuthConstants.SERVICE_NAME)
					.serviceRequestId(requestId)
					.build()
			);
		};
		if (!response.messageStatus().equals(SendAuthCodePort.MESSAGE_STATUS_COMPLETED)) {
			log.info("Failed to send authCode message. requestId: {}, messageId: {}.", requestId, response.messageId());
			throw new TiTiAuthException(TiTiAuthBusinessCodes.GENERATE_AUTH_CODE_FAILURE);
		}
		log.info("Successfully sent authCode message. requestId: {}, messageId: {}.", requestId, response.messageId());
	}

	private interface Logger {

		String AUTH_CODE_PUSHER_ID_FORMAT = "AUTH_CODE_NOTI_%s";

		static String makeRequestId(AuthCode authCode) {
			return String.format(AUTH_CODE_PUSHER_ID_FORMAT,
				HashingUtils.hashSha256(authCode.authType().getShortenName(), authCode.targetType().getShortenName(), authCode.targetValue())
			);
		}

	}

}
