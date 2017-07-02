package com.devopsbuddy.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.devopsbuddy.web.domain.frontend.FeedbackPojo;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.to.address}")
	private String defaultToAddress;

	protected SimpleMailMessage prepareSimpleMailMessageFromFeedbackPojo(FeedbackPojo feedbackPojo) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(defaultToAddress);
		message.setFrom(feedbackPojo.getEmail());
		message.setSubject("[DevOps Buddy]: Feedback received from " + feedbackPojo.getFirstName() + " "
				+ feedbackPojo.getLastName() + "!");
		message.setText(
				"User with email: " + feedbackPojo.getEmail() + " left this feedback:\n" + feedbackPojo.getFeedback());

		return message;
	}

	@Override
	public void sendFeedbackEmail(FeedbackPojo feedbackPojo) {
		SimpleMailMessage message = prepareSimpleMailMessageFromFeedbackPojo(feedbackPojo);
		sendGenericEmailMessage(message);
	}
}
