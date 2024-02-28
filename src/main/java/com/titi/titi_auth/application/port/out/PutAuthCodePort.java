package com.titi.titi_auth.application.port.out;

import com.titi.titi_auth.domain.AuthCode;

public interface PutAuthCodePort {

	String put(AuthCode authCode);

}
