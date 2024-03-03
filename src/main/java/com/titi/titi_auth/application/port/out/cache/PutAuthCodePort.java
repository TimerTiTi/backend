package com.titi.titi_auth.application.port.out.cache;

import lombok.Builder;

public interface PutAuthCodePort {

	void invoke(Command command);

	@Builder
	record Command(
		String authKey,
		String authCode
	) {

	}

}
