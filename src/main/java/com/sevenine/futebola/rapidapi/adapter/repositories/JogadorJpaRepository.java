package com.sevenine.futebola.rapidapi.adapter.repositories;

import com.sevenine.futebola.rapidapi.adapter.repositories.data.JogadorData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JogadorJpaRepository extends JpaRepository<JogadorData, Long> {
    Optional<JogadorData> findByExternoId(Long externoId);
}
