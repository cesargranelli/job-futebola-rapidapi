package com.sevenine.futebola.rapidapi.core.converters;

import com.sevenine.futebola.rapidapi.adapter.endpoints.rapidapi.response.PlayerResponse;
import com.sevenine.futebola.rapidapi.domain.entities.Jogadores;
import com.sevenine.futebola.rapidapi.domain.enumerates.Fornecedor;
import com.sevenine.futebola.rapidapi.domain.enumerates.Posicao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PlayerToJogadorConverter implements
        Converter<List<Map<String, PlayerResponse>>, List<Jogadores>> {

    Logger logger = LoggerFactory.getLogger(PlayerToJogadorConverter.class);

    @Override
    public List<Jogadores> convert(List<Map<String, PlayerResponse>> source) {
        return source.stream().map(player -> {
            try {
                Jogadores jogadores = new Jogadores();
                jogadores.setFornecedor(Fornecedor.RAPIDAPI);
                jogadores.setExternoId(player.get("player").getId());
                jogadores.setNomeCompleto(player.get("player").getName());
                jogadores.setNomeCurto(player.get("player").getShortName());
                jogadores.setPosicao(Posicao.valueOf(player.get("player").getPosition()).getCodigo());
                jogadores.setNumeroCamisa(player.get("player").getShirtNumber());
                return jogadores;
            } catch (Exception e) {
                logger.warn("{}", player.get("player"));
                return new Jogadores();
            }
        }).toList();
    }

}
