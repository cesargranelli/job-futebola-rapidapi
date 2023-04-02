package com.sevenine.futebola.evento.domain.ports;

import com.sevenine.futebola.evento.domain.entities.Parametros;

public interface ConsultaConfiguracoesPort {
    Parametros consulta(final String codigo);
}
