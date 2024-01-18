package com.titi.titi_pusher.application.port.out.smtp;

import com.titi.titi_pusher.domain.email.SimpleMailMessage;

public interface SendMailPort {

	boolean sendSimpleMessage(SimpleMailMessage message);

}
