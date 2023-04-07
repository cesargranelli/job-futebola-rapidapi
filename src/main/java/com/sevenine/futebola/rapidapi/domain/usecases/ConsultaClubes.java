package com.sevenine.futebola.rapidapi.domain.usecases;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sevenine.futebola.rapidapi.adapter.repositories.ClubeJpaRepository;
import com.sevenine.futebola.rapidapi.adapter.repositories.data.ClubeData;
import com.sevenine.futebola.rapidapi.domain.entities.Clubes;
import com.sevenine.futebola.rapidapi.domain.ports.ConsultaClubesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ConsultaClubes implements ConsultaClubesPort {

    private final ObjectMapper objectMapper;
    private final ClubeJpaRepository jpaRepository;

    @Override
    public List<Clubes> consulta(final Long fornecedorId) {
        List<ClubeData> clubeDataList = jpaRepository.findByFornecedorId(fornecedorId);

        return objectMapper.convertValue(clubeDataList, new TypeReference<>() {
        });
    }
}
