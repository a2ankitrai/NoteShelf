package com.ank.noteshelf.event;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ank.ankoauth2client.common.AnkOauth2Properties;
import com.ank.ankoauth2client.resource.AuthType;
import com.ank.ankoauth2client.resource.UserDto;
import com.ank.noteshelf.dto.TokenDto;
import com.ank.noteshelf.service.EmailService;
import com.ank.noteshelf.service.TokenService;

@Component
public class UserEventListener {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AnkOauth2Properties properties;

    @EventListener
    public void handleUserRegistrationEmailEvent(UserRegistrationEmailEvent userRegistrationEmailEvent) {

	UserDto userDto = userRegistrationEmailEvent.getUserDto();

	String templateName;
	String to = userDto.getEmail();
	String subject = "Welcome to NoteShelf!";
	String text = null;
	if (userDto.getAuthType() == AuthType.APP) {

	    TokenDto tokenDto = tokenService.createToken("REGISTRATION_EMAIL_TOKEN", userDto.getUserId());
	    Map<String, Object> variablesMap = new HashMap<>();
	    variablesMap.put("userName", userDto.getUserName());
	    variablesMap.put("emailVerificationlink", properties.getApplicationUrl() + tokenDto.getTokenValue());
	    templateName = "app-user-registration-email-verification";
	    text = buildContent(templateName, variablesMap);

	} else {
	    templateName = "oauth-registered-user-confirmation-email";
	    Map<String, Object> variablesMap = new HashMap<>();
	    variablesMap.put("userName", userDto.getUserName());
	    variablesMap.put("noteShelfSiteLink", properties.getApplicationUrl());
	    text = buildContent(templateName, variablesMap);
	}
	emailService.sendEmail(to, subject, text, true);
    }

    @EventListener
    public void handleUserPasswordResetEmailEvent(UserPasswordResetEmailEvent userPasswordResetEmailEvent) {
	UserDto userDto = userPasswordResetEmailEvent.getUserDto();
	System.out.println("inside handleUserPasswordResetEmailEvent");
	TokenDto tokenDto = tokenService.createToken("PASSWORD_RESET_TOKEN", userDto.getUserId());

	Map<String, Object> variablesMap = new HashMap<>();
	variablesMap.put("userName", userDto.getUserName());
	variablesMap.put("resetPasswordLink", properties.getApplicationUrl() +"user/reset-password/"+ tokenDto.getTokenValue());

	String templateName = "reset-password-email";

	String to = userDto.getEmail();
	String subject = "Password Reset Request - NoteShelf";
	String text = buildContent(templateName, variablesMap);

	emailService.sendEmail(to, subject, text, true);
    }

    private String buildContent(String templateName, Map<String, Object> variablesMap) {
	Context context = new Context();
	context.setVariables(variablesMap);
	return templateEngine.process(templateName, context);
    }
}
