package com.titi.titi_pusher.adapter.out.smtp;

import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.titi.titi_pusher.application.port.out.smtp.SendMailPort;
import com.titi.titi_pusher.domain.email.SimpleMailMessage;

@Slf4j
@Component
@RequiredArgsConstructor
class SendMailPortAdapter implements SendMailPort {

	private final JavaMailSender javaMailSender;

	@Override
	public boolean sendSimpleMessage(SimpleMailMessage message) {
		try {
			this.javaMailSender.send(this.createSimpleMailMessage(message));
			log.info("[sendSimpleMessage] Success to send mail. notificationId: {}", message.notificationId());
			return true;
		} catch (MailSendException e) {
			log.error("[sendSimpleMessage] Fail to send mail. notificationId: {}", message.notificationId(), e);
			return false;
		}
	}

	private org.springframework.mail.SimpleMailMessage createSimpleMailMessage(SimpleMailMessage message) {
		final org.springframework.mail.SimpleMailMessage simpleMailMessage = new org.springframework.mail.SimpleMailMessage();
		simpleMailMessage.setFrom(message.from());
		simpleMailMessage.setTo(message.to());
		simpleMailMessage.setSubject(message.subject());
		simpleMailMessage.setText(message.text());
		return simpleMailMessage;
	}

}
