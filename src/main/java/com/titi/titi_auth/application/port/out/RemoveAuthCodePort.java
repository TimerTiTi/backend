package com.titi.titi_auth.application.port.out;

import lombok.Builder;

public interface RemoveAuthCodePort {

	void invoke(Command command);

	@Builder
	record Command(
		String authKey
	) {

	}

}
