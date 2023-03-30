package com.sevenine.futebola.evento.datasources.database;

import com.sevenine.futebola.evento.datasources.database.data.ConfiguracaoData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LogJpaRepository extends JpaRepository<ConfiguracaoData, Long> {
    Optional<ConfiguracaoData> findByCodigo(String codigo);
}
