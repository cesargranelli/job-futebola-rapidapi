package com.sevenine.futebola.evento.interactors.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.sevenine.futebola.evento.datasources.database.ConfiguracaoJpaRepository;
import com.sevenine.futebola.evento.datasources.database.data.ConfiguracaoData;
import com.sevenine.futebola.evento.entities.Parametros;
import com.sevenine.futebola.evento.handlers.exceptions.ConfiguracaoNaoLocalizadaException;
import com.sevenine.futebola.evento.repositories.ConsultaConfiguracaoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ConsultaConfiguracao implements ConsultaConfiguracaoPort {

    private final JsonMapper jsonMapper;
    private final ConfiguracaoJpaRepository jpaRepository;

    @Override
    public Parametros consulta(final String codigo) {
        Optional<ConfiguracaoData> optional = jpaRepository.findByCodigo(codigo);

        try {
            return jsonMapper.readValue(optional.orElseThrow().getParametros(), Parametros.class);
        } catch (NoSuchElementException e) {
            throw new ConfiguracaoNaoLocalizadaException();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
