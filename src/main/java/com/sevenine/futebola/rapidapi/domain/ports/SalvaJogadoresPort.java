package com.sevenine.futebola.rapidapi.domain.ports;

import com.sevenine.futebola.rapidapi.domain.entities.Jogadores;

import java.util.List;

public interface SalvaJogadoresPort {
    void salva(final List<Jogadores> jogadores);
}
