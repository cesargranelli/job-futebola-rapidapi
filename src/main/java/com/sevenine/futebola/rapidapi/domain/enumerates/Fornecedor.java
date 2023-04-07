package com.sevenine.futebola.rapidapi.domain.enumerates;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Fornecedor {
    RAPIDAPI(1L, "83d49e34-cabb-11ed-8305-a251453ec86a", "rapidapi", true);

    private final Long id;
    private final String codigo;
    private final String nome;
    private final boolean situacao;
}
