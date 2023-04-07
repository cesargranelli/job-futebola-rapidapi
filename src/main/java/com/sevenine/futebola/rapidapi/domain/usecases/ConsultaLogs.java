package com.sevenine.futebola.rapidapi.domain.usecases;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sevenine.futebola.rapidapi.adapter.repositories.LogJpaRepository;
import com.sevenine.futebola.rapidapi.adapter.repositories.data.LogData;
import com.sevenine.futebola.rapidapi.domain.entities.Logs;
import com.sevenine.futebola.rapidapi.domain.ports.ConsultaLogsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ConsultaLogs implements ConsultaLogsPort {

    private final ObjectMapper objectMapper;
    private final LogJpaRepository logJpaRepository;

    @SuppressWarnings("unchecked")
    @Override
    public List<Logs> consulta(final String codigo, final LocalDate dataExecucao) {
        LocalDateTime dataInicio = dataExecucao.atStartOfDay();
        LocalDateTime dataFim = dataInicio.plusDays(1);

        List<LogData> logDataList =
                logJpaRepository.findByCodigoConfiguracaoAndDataHoraExecucaoBetween(codigo, dataInicio, dataFim);

        return objectMapper.convertValue(logDataList, List.class);
    }

}
