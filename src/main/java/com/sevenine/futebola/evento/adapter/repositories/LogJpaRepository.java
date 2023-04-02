package com.sevenine.futebola.evento.adapter.repositories;

import com.sevenine.futebola.evento.adapter.repositories.data.LogData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LogJpaRepository extends JpaRepository<LogData, Long> {
    List<LogData> findByCodigoConfiguracaoDataHoraExecucaoBetween(String codigo, LocalDateTime dataInicio, LocalDateTime dataFim);
}
