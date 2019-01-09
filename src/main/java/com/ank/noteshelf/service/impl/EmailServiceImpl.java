package com.ank.noteshelf.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import com.ank.noteshelf.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    public JavaMailSender emailSender;

    public void sendEmail(String to, String subject, String text, boolean isRichText) {
	MimeMessage message = emailSender.createMimeMessage();
	MimeMessageHelper helper;
	try {
	    helper = new MimeMessageHelper(message, true);
	    helper.setTo(to);
	    helper.setSubject(subject);
	    helper.setText(text, isRichText);

	    emailSender.send(message);

	} catch (MessagingException e) {

	    // TODO
	    // handle this properly
	    e.printStackTrace();
	}
    }

}
