package com.sevenine.futebola.rapidapi.adapter.repositories;

import com.sevenine.futebola.rapidapi.adapter.repositories.data.ConfiguracaoData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfiguracaoJpaRepository extends JpaRepository<ConfiguracaoData, Long> {
    Optional<ConfiguracaoData> findByCodigo(String codigo);
}
