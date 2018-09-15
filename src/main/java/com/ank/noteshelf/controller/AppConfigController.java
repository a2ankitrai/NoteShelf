package com.ank.noteshelf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ank.noteshelf.input.ConfigurationInput;
import com.ank.noteshelf.response.AppConfigResponse;
import com.ank.noteshelf.response.NsGenericResponse;
import com.ank.noteshelf.service.impl.AppConfigServiceImpl;

@RequestMapping("/config")
@RestController
public class AppConfigController {

    @Autowired
    AppConfigServiceImpl appConfigService;

    @GetMapping
    public ResponseEntity<AppConfigResponse> getConfiguration(@RequestParam(value = "configName") String configName) {
	ResponseEntity<AppConfigResponse> response = null;
	AppConfigResponse appConfigResponse = appConfigService.getConfigurationByName(configName);

	HttpStatus status = appConfigResponse != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
	response = new ResponseEntity<>(appConfigResponse, status);

	return response;

    }

    @PostMapping
    public ResponseEntity<NsGenericResponse> createConfiguration(@RequestBody ConfigurationInput configInput) {
	ResponseEntity<NsGenericResponse> response = null;

	NsGenericResponse nsGenericResponse = appConfigService.createConfiguration(configInput);

	HttpStatus status = nsGenericResponse != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
	response = new ResponseEntity<>(nsGenericResponse, status);

	return response;

    }

    @PutMapping
    public ResponseEntity<NsGenericResponse> updateConfiguration(@RequestBody ConfigurationInput configInput) {
	ResponseEntity<NsGenericResponse> response = null;

	NsGenericResponse nsGenericResponse = appConfigService.updateConfigurationByName(configInput);

	HttpStatus status = nsGenericResponse != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
	response = new ResponseEntity<>(nsGenericResponse, status);

	return response;

    }
}
