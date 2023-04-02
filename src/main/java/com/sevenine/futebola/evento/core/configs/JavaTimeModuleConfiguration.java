package com.sevenine.futebola.evento.core.configs;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaTimeModuleConfiguration {

    @Bean
    public JsonMapper jsonMapper() {
        return JsonMapper.builder().addModule(new JavaTimeModule()).build();
    }

}
