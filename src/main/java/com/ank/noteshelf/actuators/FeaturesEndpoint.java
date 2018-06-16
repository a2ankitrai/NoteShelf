package com.ank.noteshelf.actuators;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;



/**
 * The path of our endpoint is determined by the id parameter of @Endpoint, in our case, it’ll route requests to /actuator/features.
 * */

@Component
@Endpoint(id = "features", enableByDefault = true)
public class FeaturesEndpoint {

	private Map<String, Feature> features = new ConcurrentHashMap<>();
	
	@ReadOperation
    public Map<String, Feature> features() {
		
		Feature feature1 = new Feature();
		feature1.setEnabled(true);
		features.put("Awesome", feature1);
		
        return features;
    }
	
	@ReadOperation
    public Feature feature(@Selector String name) {
        return features.get(name);
    }
	
	@WriteOperation
    public void configureFeature(@Selector String name, Feature feature) {
		System.out.println(feature.getEnabled()+" added");
        features.put(name, feature);
    }
	
	@DeleteOperation
    public void deleteFeature(@Selector String name) {
        features.remove(name);
    }
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class Feature {
	 
		@JsonProperty("enabled")
        private Boolean enabled;

		public Boolean getEnabled() {
			return enabled;
		}

		public void setEnabled(Boolean enabled) {
			this.enabled = enabled;
		}
        
        
    }
	
}
