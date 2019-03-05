package com.ank.noteshelf.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ank.ankoauth2client.security.AnkOauth2SecurityConfiguration;
import com.ank.noteshelf.filter.NsOauth2WebTokenAuthenticationFilter;
import com.ank.noteshelf.service.impl.NsOAuth2UserService;
import com.ank.noteshelf.service.impl.NsUserDetailServiceImpl;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private NsOAuth2UserService nsOAuth2UserService;

    @Autowired
    private AnkOauth2SecurityConfiguration ankOauth2SecurityConfiguration;

    @Autowired
    private NsOauth2WebTokenAuthenticationFilter nsOauth2WebTokenAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

	http.csrf().disable();

	http

		.cors().and()

		.authorizeRequests()

		.antMatchers("/config").hasRole("ADMIN")

		.antMatchers(

			"/user/registration",

			"/user/login",

			"/user/verify-registration-email",

			"/user/verify-password-reset-token",

			"/user/reset-password",

			"/user/activate-user-account",

			"/user/forgot-password",

			"/greeting2")
		.permitAll()

		.antMatchers("/login/oauth2/code/*").permitAll()

		.anyRequest()

		.authenticated()

		.and()

		.requestCache().requestCache(new NullRequestCache())

		.and()

		.httpBasic();

	ankOauth2SecurityConfiguration.configure(http, nsOauth2WebTokenAuthenticationFilter, nsOAuth2UserService);

    }

    @Autowired
    NsUserDetailServiceImpl nsUserDetailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

	auth.userDetailsService(nsUserDetailService).passwordEncoder(passwordEncoder);

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
	CorsConfiguration configuration = new CorsConfiguration();

	configuration.setAllowedOrigins(Arrays.asList("*"));
	configuration.addAllowedHeader("*");
	configuration.addAllowedMethod("*");
	configuration.setExposedHeaders(Arrays.asList("X-Auth-Token"));
	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	source.registerCorsConfiguration("/**", configuration);
	return source;
    }

    /**
     * Create the Security configuration authenticating with HTTP-Basic and setting
     * the auth-token-header with spring's `HeaderHttpSessionIdResolver`
     * 
     * Once the login is validated `X-Auth-Token header` with the session value
     * needs to be passed in subsequent requests.
     */
    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
	return HeaderHttpSessionIdResolver.xAuthToken();
    }

}
