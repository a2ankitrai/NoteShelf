package com.ank.noteshelf.config;

import static com.ank.noteshelf.resource.NsCommonConstant.GCP_CREDENTIALS;
import static com.ank.noteshelf.resource.NsCommonConstant.CACHE_APP_CONFIG;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.ank.noteshelf.model.NsApplicationConfiguration;
import com.ank.noteshelf.repository.ApplicationConfigRepository;
import com.ank.noteshelf.resource.NsMessageConstant;
import com.ank.noteshelf.response.AppConfigResponse;
import com.ank.noteshelf.service.impl.AppConfigServiceImpl;

@Component
@Order(value = 0)
public class NsAppRunner implements CommandLineRunner {

    @Autowired
    AppConfigServiceImpl appConfigService;

    @Autowired
    private CacheManager cacheManager;

    @Bean
    public CacheManager cacheManager() {
	return new ConcurrentMapCacheManager(CACHE_APP_CONFIG);
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(String... args) throws Exception {

	
	// update this to add all the configurations.
	AppConfigResponse appConfigResponse = appConfigService.getConfigurationByName(GCP_CREDENTIALS);
	if (appConfigResponse != null) {

	    try {
		cacheManager.getCache(CACHE_APP_CONFIG).put(appConfigResponse.getConfigurationName(),
			appConfigResponse.getConfigurationValue());
	    } catch (Exception e) {
		logger.error(NsMessageConstant.LOAD_DATA_EXCEPTION);
	    }

	}
    }

}
