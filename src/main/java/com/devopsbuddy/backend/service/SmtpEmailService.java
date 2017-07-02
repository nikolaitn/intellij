package com.devopsbuddy.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractEmailService {

	/** Application Logger */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(SmtpEmailService.class);
	
	@Autowired
	private MailSender mailSender;
	
	@Override
	public void sendGenericEmailMessage(SimpleMailMessage message) {
		LOG.debug("Sending email for {}", message);
		mailSender.send(message);
		LOG.info("Mail sent.");
	}

}
