package com.sevenine.futebola.evento.datasources.database;

import com.sevenine.futebola.evento.datasources.database.data.LogData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LogJpaRepository extends JpaRepository<LogData, Long> {
    Optional<LogData> findByCodigoConfiguracao(String codigo);
}
