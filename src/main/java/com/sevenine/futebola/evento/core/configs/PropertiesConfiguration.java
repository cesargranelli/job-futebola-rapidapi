package com.sevenine.futebola.evento.core.configs;

import com.sevenine.futebola.evento.core.properties.AppConfigExceptionProperties;
import com.sevenine.futebola.evento.core.properties.AppConfigJobProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@ConfigurationPropertiesScan(basePackageClasses = {
        AppConfigJobProperties.class,
        AppConfigExceptionProperties.class
})
@Configuration
public class PropertiesConfiguration {
}

