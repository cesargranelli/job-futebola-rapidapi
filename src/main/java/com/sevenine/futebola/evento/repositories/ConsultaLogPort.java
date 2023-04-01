package com.sevenine.futebola.evento.repositories;

import com.sevenine.futebola.evento.entities.Logs;

import java.time.LocalDate;
import java.util.List;

public interface ConsultaLogPort {
    List<Logs> consulta(final String codigo, final LocalDate dataExecucao);
}
