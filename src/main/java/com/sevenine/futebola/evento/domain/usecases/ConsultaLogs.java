package com.sevenine.futebola.evento.domain.usecases;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sevenine.futebola.evento.adapter.repositories.LogJpaRepository;
import com.sevenine.futebola.evento.adapter.repositories.data.LogData;
import com.sevenine.futebola.evento.domain.entities.Logs;
import com.sevenine.futebola.evento.domain.ports.ConsultaLogsPort;
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
