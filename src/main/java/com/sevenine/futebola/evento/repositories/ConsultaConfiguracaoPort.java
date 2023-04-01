package com.sevenine.futebola.evento.repositories;

import com.sevenine.futebola.evento.entities.Parametros;

public interface ConsultaConfiguracaoPort {
    Parametros consulta(final String codigo);
}
