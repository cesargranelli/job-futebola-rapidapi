package com.sevenine.futebola.evento.datasources.database;

import com.sevenine.futebola.evento.datasources.database.data.ConfiguracaoData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfiguracaoJpaRepository extends JpaRepository<ConfiguracaoData, Long> {
}
