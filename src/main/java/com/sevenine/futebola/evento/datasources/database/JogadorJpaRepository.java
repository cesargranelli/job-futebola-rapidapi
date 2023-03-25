package com.sevenine.futebola.evento.datasources.database;

import com.sevenine.futebola.evento.datasources.database.data.JogadorData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogadorJpaRepository extends JpaRepository<JogadorData, Long> {
}
