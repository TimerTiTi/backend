package com.titi.titi_auth.adapter.out.pusher;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.titi.titi_auth.application.common.constant.AuthConstants;
import com.titi.titi_auth.application.port.out.SendAuthCodePort;
import com.titi.titi_auth.domain.AuthCode;
import com.titi.titi_auth.domain.TargetType;
import com.titi.titi_crypto_lib.util.HashingUtils;
import com.titi.titi_pusher.adapter.in.internal.InternalSendMailGateway;
import com.titi.titi_pusher.adapter.in.internal.dto.SendMessageResponse;

@Slf4j
@Component
@RequiredArgsConstructor
class SendAuthCodePortAdapter implements SendAuthCodePort {

	private final InternalSendMailGateway internalSendMailGateway;

	@Override
	public boolean send(AuthCode authCode) {
		final String requestId = Logger.makeRequestId(authCode);
		final SendMessageResponse response = switch (authCode.targetType()) {
			case TargetType.EMAIL -> this.internalSendMailGateway.sendSimpleMessage(
				InternalSendMailGateway.SendSimpleMessageRequest.builder()
					.to(authCode.targetValue())
					.subject(AuthMessageTemplates.GENERATE_AUTH_CODE.getSubject())
					.text(String.format(AuthMessageTemplates.GENERATE_AUTH_CODE.getText(), authCode.authCode()))
					.notificationCategory(SendAuthCodePort.NOTIFICATION_CATEGORY)
					.serviceName(AuthConstants.SERVICE_NAME)
					.serviceRequestId(requestId)
					.build()
			);
		};
		if (response.messageStatus().equals(SendAuthCodePort.MESSAGE_STATUS_COMPLETED)) {
			log.info("[send] Success to send authCode message. requestId: {}, messageId: {}.", requestId, response.messageId());
			return true;
		} else {
			log.info("[send] Fail to send authCode message. requestId: {}, messageId: {}.", requestId, response.messageId());
			return false;
		}
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
