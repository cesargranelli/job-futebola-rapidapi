package com.sevenine.futebola.evento.domain.ports;

import com.sevenine.futebola.evento.domain.entities.Clubes;

import java.util.List;

public interface ConsultaClubesPort {
    List<Clubes> consulta(final Long fornecedorId);
}
