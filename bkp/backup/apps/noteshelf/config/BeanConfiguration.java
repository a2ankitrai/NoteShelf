package com.ank.apps.noteshelf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

@Configuration
public class BeanConfiguration {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * Create the Security configuration authenticating with HTTP-Basic and setting
	 * the auth-token-header with spring's `HeaderHttpSessionIdResolver`
	 */
	@Bean
	public HttpSessionIdResolver httpSessionIdResolver() {
		return HeaderHttpSessionIdResolver.xAuthToken();
	}
	
	@Bean
    JedisConnectionFactory jedisConnectionFactory() {
	return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
	RedisTemplate<String, Object> template = new RedisTemplate<>();
	template.setConnectionFactory(jedisConnectionFactory());
	return template;
    }
    
    @Bean
    public StringRedisTemplate stringRedisTemplate() {
	StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
	stringRedisTemplate.setConnectionFactory(jedisConnectionFactory());
	return stringRedisTemplate;
    }
}
