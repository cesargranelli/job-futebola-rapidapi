package com.sevenine.futebola.evento.domain.usecases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.sevenine.futebola.evento.adapter.repositories.ConfiguracaoJpaRepository;
import com.sevenine.futebola.evento.adapter.repositories.data.ConfiguracaoData;
import com.sevenine.futebola.evento.core.exceptions.ConfiguracaoNaoLocalizadaException;
import com.sevenine.futebola.evento.domain.entities.Parametros;
import com.sevenine.futebola.evento.domain.ports.ConsultaConfiguracoesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ConsultaConfiguracoes implements ConsultaConfiguracoesPort {

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
