package com.ank.noteshelf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring Boot main application class.
 * 
 * @author Ankit Rai
 */

/**
 * 
 * @EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
 * 
 * @ComponentScan(basePackages={"com.ank.apps.noteshelf"})
 * 
 * @EnableJpaRepositories(basePackages="com.ank.noteshelf.repository")
 * @EnableTransactionManagement @EntityScan(basePackages="com.ank.apps.noteshelf.model")
 */

@ComponentScan(basePackages = { "com.ank.noteshelf" })
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class NoteShelfApplication {

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//	return new BCryptPasswordEncoder();
//    }

    public static void main(String[] args) {

	/**
	 * Entry point for the application.
	 * 
	 * @param args Command line arguments.
	 */
	SpringApplication.run(NoteShelfApplication.class, args);
    }
}
