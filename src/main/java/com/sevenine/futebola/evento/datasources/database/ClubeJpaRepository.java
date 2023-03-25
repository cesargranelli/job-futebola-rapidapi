package com.sevenine.futebola.evento.datasources.database;

import com.sevenine.futebola.evento.datasources.database.data.ClubeData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubeJpaRepository extends JpaRepository<ClubeData, Long> {
}
