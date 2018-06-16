package com.ank.noteshelf.actuators;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
public class ActuatorSecurity // extends WebSecurityConfigurerAdapter
{

	/**
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		// Implement this after reviewing Spring Security 
		
		http.requestMatcher(EndpointRequest.toAnyEndpoint()).authorizeRequests()
				.anyRequest().hasRole("ENDPOINT_ADMIN")
				.and()
			.httpBasic();
			
			
	}**/
	
	/**if Spring Security is present, you would need to add custom security configuration that allows unauthenticated access to the endpoints
	 * @Override
	protected void configure(HttpSecurity http) throws Exception {
		http.requestMatcher(EndpointRequest.toAnyEndpoint()).authorizeRequests()
			.anyRequest().permitAll()
	}
	 * */
}
