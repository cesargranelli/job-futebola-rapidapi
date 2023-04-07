package com.sevenine.futebola.rapidapi.core.configs;

import com.sevenine.futebola.rapidapi.core.properties.AppConfigExceptionProperties;
import com.sevenine.futebola.rapidapi.core.properties.AppConfigJobProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@ConfigurationPropertiesScan(basePackageClasses = {
        AppConfigJobProperties.class,
        AppConfigExceptionProperties.class
})
@Configuration
public class PropertiesConfiguration {
}

