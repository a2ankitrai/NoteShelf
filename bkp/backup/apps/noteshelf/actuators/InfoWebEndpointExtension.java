package com.ank.apps.noteshelf.actuators;

import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.endpoint.web.annotation.EndpointWebExtension;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.stereotype.Component;

//@Component
//@EndpointWebExtension(endpoint = InfoEndpoint.class)
public class InfoWebEndpointExtension {

	private InfoEndpoint delegate;

	// standard constructor

	//@ReadOperation
	public WebEndpointResponse<Map> info() {
		Map<String, Object> info = this.delegate.info();
		Integer status = getStatus(info);
		return new WebEndpointResponse<>(info, status);
	}

	private Integer getStatus(Map<String, Object> info) {
		// return 5xx if this is a snapshot
		return 200;
	}
}
