package com.ank.noteshelf.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.ank.ankoauth2client.resource.UserDto;

@Component
public class UserEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishUserRegistrationEmailEvent(final UserDto userDto) {
	System.out.println("publishing registration email event");
	UserRegistrationEmailEvent userRegistrationEmailEvent = new UserRegistrationEmailEvent(this, userDto);
	applicationEventPublisher.publishEvent(userRegistrationEmailEvent);
    }

    public void publishPasswordResetEmailEvent(final UserDto userDto) {
	System.out.println("publishing password reset email event");
	UserPasswordResetEmailEvent userPasswordResetEmailEvent = new UserPasswordResetEmailEvent(this, userDto);
	applicationEventPublisher.publishEvent(userPasswordResetEmailEvent);
    }
}
