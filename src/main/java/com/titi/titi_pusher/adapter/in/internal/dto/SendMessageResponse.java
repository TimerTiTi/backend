package com.titi.titi_pusher.adapter.in.internal.dto;

import lombok.Builder;

@Builder
public record SendMessageResponse(
	String messageId,
	String messageStatus
) {

}
