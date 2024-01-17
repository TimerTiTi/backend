package com.titi.pusher.application.port.out.smtp;

import com.titi.pusher.domain.email.SimpleMailMessage;

public interface SendMailPort {

	boolean sendSimpleMessage(SimpleMailMessage message);

}
