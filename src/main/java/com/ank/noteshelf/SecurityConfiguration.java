package com.ank.noteshelf;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
 
public class SecurityConfiguration 
//extends WebSecurityConfigurerAdapter {
{
	@Autowired
	private DataSource dataSource;
	
	//@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		/**
		 * guarantee that the application will not create any session at all.
		 */
		// http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
	}

	/**
	 * Security Configuration for actuators - in develop
	 */
	/**
	 * @Bean public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity
	 *       http) { return
	 *       http.authorizeExchange().pathMatchers("/manage/**").permitAll().anyExchange().authenticated().and()
	 *       .build(); }
	 * 
	 * @Bean public ServerHttpSecurity serverHttpSecurity() { return
	 *       ServerHttpSecurity.http(); }
	 */
}
