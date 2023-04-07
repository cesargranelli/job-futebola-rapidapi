package com.sevenine.futebola.rapidapi.domain.usecases;

import com.sevenine.futebola.rapidapi.adapter.endpoints.rapidapi.RapidApiFeignClient;
import com.sevenine.futebola.rapidapi.adapter.endpoints.rapidapi.response.PlayerResponse;
import com.sevenine.futebola.rapidapi.domain.entities.Clubes;
import com.sevenine.futebola.rapidapi.domain.entities.Jogadores;
import com.sevenine.futebola.rapidapi.domain.ports.ConsultaJogadoresPort;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
@Component
public class ConsultaJogadores implements ConsultaJogadoresPort {

    private final RapidApiFeignClient feignClient;
    private final Converter<List<Map<String, PlayerResponse>>, List<Jogadores>> converter;

    @Override
    public List<Jogadores> consulta(final List<Clubes> clubes) {
        List<Jogadores> jogadores = new ArrayList<>();
        clubes.forEach(clube -> {
            Map<String, List<Map<String, PlayerResponse>>> response =
                    feignClient.getPlayers(clube.getExternoId());

            jogadores.addAll(requireNonNull(converter.convert(response.get("players"))));
        });
        return jogadores;
    }

}
