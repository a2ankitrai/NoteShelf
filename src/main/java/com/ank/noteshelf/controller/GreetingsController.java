package com.ank.noteshelf.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingsController {

    public static final Logger logger = LoggerFactory.getLogger(GreetingsController.class);
    private static final String template = "Hello, User no. %s : %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/")
    public String welcome(@RequestParam(name = "name", required = false, defaultValue = "Stranger") String name) {

	logger.info("User entered : " + name);
	return String.format(template, counter.incrementAndGet(), name);
    }

    @GetMapping("/greeting")
    public String sayHello(@RequestParam(name = "name", required = false, defaultValue = "Stranger") String name) {

	logger.info("User entered : " + name);
	return String.format(template, counter.incrementAndGet(), name);
    }

    @GetMapping("/greeting2")
    public ResponseEntity<String> greet2() {

	String responseText = String.format(template, counter.incrementAndGet(), "Jack fuscher");

	HttpStatus status = HttpStatus.OK;
	ResponseEntity<String> response = new ResponseEntity<>(responseText, status);
	return response;
    }

}
