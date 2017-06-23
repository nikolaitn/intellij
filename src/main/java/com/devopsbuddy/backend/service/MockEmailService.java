package com.devopsbuddy.backend.service;

import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService {

	/** Application Logger */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void sendGeneraicEmailMessage(SimpleMailMessage message) {
		LOG.debug("Simulating email service...");
		LOG.info(message.toString());
		LOG.debug("Email sent");
	}

}
