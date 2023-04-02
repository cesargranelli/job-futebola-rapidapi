package com.sevenine.futebola.evento.core.exceptions;

public class ExecucaoJobException extends ApplicationException {
    public ExecucaoJobException(final String codigo, final String descricao) {
        super(codigo, descricao);
    }
}
