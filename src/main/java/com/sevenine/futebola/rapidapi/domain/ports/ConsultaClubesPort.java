package com.sevenine.futebola.rapidapi.domain.ports;

import com.sevenine.futebola.rapidapi.domain.entities.Clubes;

import java.util.List;

public interface ConsultaClubesPort {
    List<Clubes> consulta(final Long fornecedorId);
}
