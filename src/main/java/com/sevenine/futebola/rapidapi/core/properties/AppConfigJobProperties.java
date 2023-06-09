package com.sevenine.futebola.rapidapi.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.config.job")
public class AppConfigJobProperties {
    private String codigoAtualizaJogadores;
}
