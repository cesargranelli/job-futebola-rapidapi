package com.sevenine.futebola.evento.interactors.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sevenine.futebola.evento.datasources.database.LogJpaRepository;
import com.sevenine.futebola.evento.datasources.database.data.LogData;
import com.sevenine.futebola.evento.entities.Logs;
import com.sevenine.futebola.evento.repositories.ConsultaLogPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ConsultaLog implements ConsultaLogPort {

    private final ObjectMapper objectMapper;
    private final LogJpaRepository logJpaRepository;

    @SuppressWarnings("unchecked")
    @Override
    public List<Logs> consulta(String codigo, LocalDate dataExecucao) {
        LocalDateTime dataInicio = dataExecucao.atStartOfDay();
        LocalDateTime dataFim = dataInicio.plusDays(1);

        List<LogData> logDataList =
                logJpaRepository.findByCodigoConfiguracaoDataHoraExecucaoBetween(codigo, dataInicio, dataFim);

        return objectMapper.convertValue(logDataList, List.class);
    }

}
