package com.titi.titi_auth.application.port.out;

import com.titi.titi_auth.domain.AuthCode;

public interface SendAuthCodePort {

	String NOTIFICATION_CATEGORY = "AUTHENTICATION";
	String MESSAGE_STATUS_COMPLETED = "COMPLETED";

	void send(AuthCode authCode);

}
