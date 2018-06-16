package com.ank.apps.noteshelf.actuators;

import java.util.Random;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

public class HealthCheck implements HealthIndicator {

	private Random random = new Random();
	
	@Override
	public Health health() {
		Health healthResult = null;
		
		healthResult = Health.down().build();
		
		return healthResult;
	}

}
