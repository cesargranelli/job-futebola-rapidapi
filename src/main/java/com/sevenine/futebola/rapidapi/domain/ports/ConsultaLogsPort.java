package com.sevenine.futebola.rapidapi.domain.ports;

import com.sevenine.futebola.rapidapi.domain.entities.Logs;

import java.time.LocalDate;
import java.util.List;

public interface ConsultaLogsPort {
    List<Logs> consulta(final String codigo, final LocalDate dataExecucao);
}
