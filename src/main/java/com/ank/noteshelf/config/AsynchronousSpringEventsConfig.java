package com.ank.noteshelf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
public class AsynchronousSpringEventsConfig {

    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
	SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();

	/*
	 * SimpleAsyncTaskExecutor
	 * 
	 * {@link TaskExecutor} implementation that fires up a new Thread for each task,
	 * executing it asynchronously.
	 *
	 * <p>Supports limiting concurrent threads through the "concurrencyLimit" bean
	 * property. By default, the number of concurrent threads is unlimited.
	 *
	 * <p><b>NOTE: This implementation does not reuse threads!</b> Consider a
	 * thread-pooling TaskExecutor implementation instead, in particular for
	 * executing a large number of short-lived tasks.
	 */
	SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
	taskExecutor.setConcurrencyLimit(20);
	eventMulticaster.setTaskExecutor(taskExecutor);
	return eventMulticaster;
    }
}
