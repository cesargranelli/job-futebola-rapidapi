package com.sevenine.futebola.evento.domain.ports;

import com.sevenine.futebola.evento.domain.entities.Jogadores;

import java.util.List;

public interface SalvaJogadoresPort {
    void salva(final List<Jogadores> jogadores);
}
