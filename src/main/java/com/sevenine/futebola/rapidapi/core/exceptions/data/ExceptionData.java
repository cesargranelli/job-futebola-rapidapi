package com.sevenine.futebola.rapidapi.core.exceptions.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionData {
    private String codigo;
    private String tipo;
    private String descricao;
}
