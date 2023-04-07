package com.sevenine.futebola.evento.domain.usecases;

import com.sevenine.futebola.evento.adapter.repositories.JogadorJpaRepository;
import com.sevenine.futebola.evento.adapter.repositories.data.JogadorData;
import com.sevenine.futebola.evento.domain.entities.Jogadores;
import com.sevenine.futebola.evento.domain.ports.SalvaJogadoresPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class SalvaJogadores implements SalvaJogadoresPort {

    Logger logger = LoggerFactory.getLogger(SalvaJogadores.class);

    private final JogadorJpaRepository jpaRepository;
    private final Converter<Jogadores, JogadorData> converter;

    @Override
    public void salva(List<Jogadores> jogadores) {
        jogadores.forEach(jogador -> {
            if (jpaRepository.findByExternoId(jogador.getExternoId()).isEmpty())
                try {
                    jpaRepository.save(Objects.requireNonNull(converter.convert(jogador)));
                } catch (Exception e) {
                    logger.warn("nome do atleta {}", jogador.getNomeCompleto());
                }
        });
    }

}
