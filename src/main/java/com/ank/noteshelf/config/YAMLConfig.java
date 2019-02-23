package com.ank.noteshelf.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YAMLConfig {

    private String name;
}
