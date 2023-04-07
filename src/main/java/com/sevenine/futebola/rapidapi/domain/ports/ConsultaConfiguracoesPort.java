package com.sevenine.futebola.rapidapi.domain.ports;

import com.sevenine.futebola.rapidapi.domain.entities.Parametros;

public interface ConsultaConfiguracoesPort {
    Parametros consulta(final String codigo);
}
