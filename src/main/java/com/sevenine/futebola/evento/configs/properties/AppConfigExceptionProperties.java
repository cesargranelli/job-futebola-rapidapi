package com.sevenine.futebola.evento.configs.properties;

import com.sevenine.futebola.evento.handlers.exceptions.data.ExceptionData;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.config.exception")
public class AppConfigExceptionProperties {
    private ExceptionData codigoNaoConfigurado;
}
