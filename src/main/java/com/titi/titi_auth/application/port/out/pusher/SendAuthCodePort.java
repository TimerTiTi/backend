package com.titi.titi_auth.application.port.out.pusher;

import com.titi.titi_auth.domain.AuthCode;

public interface SendAuthCodePort {

	String NOTIFICATION_CATEGORY = "AUTHENTICATION";
	String MESSAGE_STATUS_COMPLETED = "COMPLETED";

	void invoke(AuthCode authCode);

}
