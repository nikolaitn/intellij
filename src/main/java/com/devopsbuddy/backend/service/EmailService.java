package com.devopsbuddy.backend.service;


import org.springframework.mail.SimpleMailMessage;

import com.devopsbuddy.web.domain.frontend.FeedbackPojo;

public interface EmailService {
	
	void sendFeedbackEmail(FeedbackPojo feedbackPojo);
	
	void sendGenericEmailMessage(SimpleMailMessage message);

}
