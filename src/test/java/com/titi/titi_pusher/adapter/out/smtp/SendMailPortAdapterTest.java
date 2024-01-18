package com.titi.titi_pusher.adapter.out.smtp;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.titi.titi_pusher.domain.email.SimpleMailMessage;

@Disabled("Do not automate actual sending mail tests.")
@SpringBootTest
class SendMailPortAdapterTest {

	@Autowired
	private SendMailPortAdapter sendMailPortAdapter;

	/**
	 * Set the environment variables ${TITI_SMTP_GMAIL_TEST_USERNAME} and ${TITI_SMTP_GMAIL_TEST_PASSWORD} before running the tests.
	 */
	@Test
	void sendSimpleMessageTest() {
		// given
		final SimpleMailMessage simpleMailMessage = SimpleMailMessage.builder()
			.from("TimerTiTi.dev@gmail.com")
			.to("ksp970306@gmail.com")
			.subject("subject")
			.text("text")
			.build();

		// when
		final boolean isSuccessful = sendMailPortAdapter.sendSimpleMessage(simpleMailMessage);

		// then
		assertThat(isSuccessful).isTrue();
	}

}
