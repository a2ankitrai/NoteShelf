package com.ank.apps.noteshelf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring Boot main application class.
 * 
 * @author Ankit Rai
 */
@SpringBootApplication 
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.ank.apps.noteshelf"})
@EnableJpaRepositories(basePackages="com.ank.apps.noteshelf.repository")
@EnableTransactionManagement
@EntityScan(basePackages="com.ank.apps.noteshelf.model")
public class NoteShelfApplication {

	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	public static void main(String[] args) {
		
		/**
	     * Entry point for the application.
	     * 
	     * @param args Command line arguments.
	     */
		SpringApplication.run(NoteShelfApplication.class, args);
	}
}
