package com.ank.noteshelf.service.impl;

import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ank.noteshelf.input.ConfigurationInput;
import com.ank.noteshelf.mapstruct.AppConfigurationMapper;
import com.ank.noteshelf.model.NsApplicationConfiguration;
import com.ank.noteshelf.repository.ApplicationConfigRepository;
import com.ank.noteshelf.resource.NsMessageConstant;
import com.ank.noteshelf.response.AppConfigResponse;
import com.ank.noteshelf.response.NsGenericResponse;
import com.ank.noteshelf.util.UuidUtils;

@Service
public class AppConfigServiceImpl {

    @Autowired
    ApplicationConfigRepository applicationConfigRepository;

    public NsGenericResponse createConfiguration(ConfigurationInput configInput) {

	NsGenericResponse response = null;

	Date now = new Date();
	NsApplicationConfiguration nsApplicationConfiguration = AppConfigurationMapper.INSTANCE
		.mapConfigInputToAppConfiguration(configInput, UuidUtils.generateRandomUuid(), now);

	nsApplicationConfiguration = applicationConfigRepository.save(nsApplicationConfiguration);

	response = new NsGenericResponse();
	if (nsApplicationConfiguration != null) {
	    response.setMessage(NsMessageConstant.CONFIG_SAVE_SUCCESS);
	} else {
	    response.setMessage(NsMessageConstant.CONFIG_SAVE_ERROR);
	}
	response.setTimeStamp(new Date());

	return response;
    }

//    Place caching here..
    public AppConfigResponse getConfigurationByName(String configName) {

	NsApplicationConfiguration nsApplicationConfiguration = applicationConfigRepository
		.findByConfigurationName(configName);
	AppConfigResponse appConfigResponse = null;

	if (nsApplicationConfiguration != null) {
	    appConfigResponse = AppConfigurationMapper.INSTANCE
		    .mapNsAppConfigurationToAppConfigResponse(nsApplicationConfiguration);
	}

	return appConfigResponse;
    }

//    place caching here..
    public NsGenericResponse updateConfigurationByName(ConfigurationInput configInput) {

	NsApplicationConfiguration appConfig = applicationConfigRepository
		.findByConfigurationName(configInput.getConfigurationName());

	if (appConfig == null) {
	    throw new EmptyResultDataAccessException(NsMessageConstant.NO_CONFIG_FOUND_BY_NAME, 1);
	}

	appConfig.setConfigurationValue(
		Base64.getEncoder().encodeToString(configInput.getConfigurationValue().getBytes()));
	Date now = new Date();
	appConfig.setUpdatedDate(now);

	appConfig = applicationConfigRepository.save(appConfig);

	NsGenericResponse response = null;
	response = new NsGenericResponse();
	if (appConfig != null) {
	    response.setMessage(NsMessageConstant.CONFIG_UPDATE_SUCCESS);
	} else {
	    response.setMessage(NsMessageConstant.CONFIG_UPDATE_ERROR);
	}
	response.setTimeStamp(new Date());

	return response;
    }

}
