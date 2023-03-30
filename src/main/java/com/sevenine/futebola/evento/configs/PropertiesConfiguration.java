package com.sevenine.futebola.evento.configs;

import com.sevenine.futebola.evento.configs.properties.AppConfigExceptionProperties;
import com.sevenine.futebola.evento.configs.properties.AppConfigJobProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@ConfigurationPropertiesScan(basePackageClasses = {
        AppConfigJobProperties.class,
        AppConfigExceptionProperties.class
})
@Configuration
public class PropertiesConfiguration {
}

