package com.devopsbuddy.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.devopsbuddy.backend.service.EmailService;
import com.devopsbuddy.web.domain.frontend.FeedbackPojo;

@Controller
public class ContactController {

	/** Application Logger */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(ContactController.class);

	public static final String CONTACT_US_VIEW_NAME = "contact/contact";

	/** The key which identifies the feedback payload in the Model */
	public static final String FEEDBACK_MODEL_KEY = "feedback";

	@Autowired
	private EmailService emailService;
	
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String contactGet(ModelMap model) {
		FeedbackPojo feedbackPojo = new FeedbackPojo();
		model.put(FEEDBACK_MODEL_KEY, feedbackPojo);
		return CONTACT_US_VIEW_NAME;
	}

	@RequestMapping(value = "/contact", method = RequestMethod.POST)
	public String contactPost(@ModelAttribute(FEEDBACK_MODEL_KEY) FeedbackPojo feedback) {
		LOG.debug("Feedback POJO content: {}", feedback);
		emailService.sendFeedbackEmail(feedback);
		return CONTACT_US_VIEW_NAME;
	}
}
