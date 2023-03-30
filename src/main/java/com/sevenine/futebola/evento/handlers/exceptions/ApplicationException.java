package com.sevenine.futebola.evento.handlers.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ApplicationException extends RuntimeException {
    private String codigo;
    private String descricao;

    public ApplicationException(final String codigo, final String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
}
