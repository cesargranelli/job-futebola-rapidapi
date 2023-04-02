package com.sevenine.futebola.evento.adapter.repositories;

import com.sevenine.futebola.evento.adapter.repositories.data.JogadorData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogadorJpaRepository extends JpaRepository<JogadorData, Long> {
}
