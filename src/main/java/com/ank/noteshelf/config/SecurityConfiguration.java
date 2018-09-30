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

import com.ank.noteshelf.service.impl.NsUserDetailServiceImpl;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.csrf().disable();

    http

        .cors().and()

        .authorizeRequests()

        .antMatchers("/config").hasRole("ADMIN")

        .antMatchers("/user/registration").permitAll()

        .anyRequest().authenticated()

        .and()

        .requestCache().requestCache(new NullRequestCache())

        .and()

        .httpBasic();

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
   * Create the Security configuration authenticating with HTTP-Basic and setting the
   * auth-token-header with spring's `HeaderHttpSessionIdResolver`
   * 
   * Once the login is validated `X-Auth-Token header` with the session value needs to be passed in
   * subsequent requests.
   */
  @Bean
  public HttpSessionIdResolver httpSessionIdResolver() {
    return HeaderHttpSessionIdResolver.xAuthToken();
  }

}
