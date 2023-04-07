package com.sevenine.futebola.rapidapi.core.properties;

import com.sevenine.futebola.rapidapi.core.exceptions.data.ExceptionData;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.config.exception")
public class AppConfigExceptionProperties {
    private ExceptionData codigoNaoConfigurado;
    private ExceptionData foraDoHorarioDeExecucao;
    private ExceptionData horarioJaExecutado;
}
