package com.ank.noteshelf.event;

import org.springframework.context.ApplicationEvent;

import com.ank.ankoauth2client.resource.UserDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserPasswordResetEmailEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    private UserDto userDto;

    public UserPasswordResetEmailEvent(Object source, UserDto userDto) {
	super(source);
	this.userDto = userDto;
    }

}
