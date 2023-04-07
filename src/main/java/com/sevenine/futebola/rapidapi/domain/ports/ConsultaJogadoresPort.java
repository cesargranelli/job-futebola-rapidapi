package com.sevenine.futebola.rapidapi.domain.ports;

import com.sevenine.futebola.rapidapi.domain.entities.Clubes;
import com.sevenine.futebola.rapidapi.domain.entities.Jogadores;

import java.util.List;

public interface ConsultaJogadoresPort {
    List<Jogadores> consulta(final List<Clubes> clubes);
}
