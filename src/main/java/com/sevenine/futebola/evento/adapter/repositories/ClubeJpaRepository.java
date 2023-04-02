package com.sevenine.futebola.evento.adapter.repositories;

import com.sevenine.futebola.evento.adapter.repositories.data.ClubeData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubeJpaRepository extends JpaRepository<ClubeData, Long> {
}
