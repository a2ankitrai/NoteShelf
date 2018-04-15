package com.ank.apps.noteshelf;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
	}

	/**
	 * Security Configuration for actuators - in develop
	 * */
	/**
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		return http.authorizeExchange().pathMatchers("/manage/**").permitAll().anyExchange().authenticated().and()
				.build();
	}
	
	@Bean
	public ServerHttpSecurity serverHttpSecurity() {
		return ServerHttpSecurity.http();
	}
	*/
}
