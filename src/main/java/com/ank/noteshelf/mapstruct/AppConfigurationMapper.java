package com.ank.noteshelf.mapstruct;

import java.util.Date;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ank.noteshelf.input.ConfigurationInput;
import com.ank.noteshelf.model.NsApplicationConfiguration;
import com.ank.noteshelf.response.AppConfigResponse;

@Mapper(uses = { AppConfigMapperFunctions.class, NsCommonMapperFunctions.class })
public interface AppConfigurationMapper {

    AppConfigurationMapper INSTANCE = Mappers.getMapper(AppConfigurationMapper.class);

    @Mapping(source = "nsApplicationConfiguration.configurationValue", target = "configurationValue", qualifiedByName = "mapConfigValueDecoded")
    @Mapping(source = "nsApplicationConfiguration.isLazyLoadDisabled", target = "lazyLoadDisabled", qualifiedByName = "mapIsLazyLoadDisabledByteToBoolean")
    AppConfigResponse mapNsAppConfigurationToAppConfigResponse(NsApplicationConfiguration nsApplicationConfiguration);

    @Mapping(source = "configurationId", target = "configurationId", qualifiedByName = "mappUuidToByte")
    @Mapping(source = "configInput.configurationName", target = "configurationName")
    @Mapping(source = "configInput.configurationValue", target = "configurationValue", qualifiedByName = "mapConfigValueEncoded")
    @Mapping(source = "configInput.lazyLoadDisabled", target = "isLazyLoadDisabled", qualifiedByName = "mapIsLazyLoadDisabledBooleanToByte")
    @Mapping(source = "date", target = "createdDate")
    @Mapping(source = "date", target = "updatedDate")
    NsApplicationConfiguration mapConfigInputToAppConfiguration(ConfigurationInput configInput, UUID configurationId,
	    Date date);
}
