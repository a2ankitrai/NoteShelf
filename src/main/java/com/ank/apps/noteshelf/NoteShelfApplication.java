package com.ank.apps.noteshelf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Boot main application class.
 * 
 * @author Ankit Rai
 */

/**
 * 
 * @EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class
 *                                  }) @ComponentScan(basePackages={"com.ank.apps.noteshelf"})
 * @EnableJpaRepositories(basePackages="com.ank.apps.noteshelf.repository") @EnableTransactionManagement @EntityScan(basePackages="com.ank.apps.noteshelf.model")
 */

@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan(basePackages = { "com.ank.apps.noteshelf" })
public class NoteShelfApplication {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {

		/**
		 * Entry point for the application.
		 * 
		 * @param args
		 *            Command line arguments.
		 */
		SpringApplication.run(NoteShelfApplication.class, args);
	}
}
