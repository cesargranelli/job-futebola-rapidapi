package com.sevenine.futebola.evento.domain.ports;

import com.sevenine.futebola.evento.domain.entities.Clubes;
import com.sevenine.futebola.evento.domain.entities.Jogadores;

import java.util.List;

public interface ConsultaJogadoresPort {
    List<Jogadores> consulta(final List<Clubes> clubes);
}
