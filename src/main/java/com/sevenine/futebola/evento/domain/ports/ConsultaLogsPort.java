package com.sevenine.futebola.evento.domain.ports;

import com.sevenine.futebola.evento.domain.entities.Logs;

import java.time.LocalDate;
import java.util.List;

public interface ConsultaLogsPort {
    List<Logs> consulta(final String codigo, final LocalDate dataExecucao);
}
