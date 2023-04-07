package com.sevenine.futebola.rapidapi.domain.enumerates;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Posicao {
    G(1L, "GOL", "Goleiro"),
    D(2L, "DEF", "Defesa"),
    L(3L, "LAT", "Lateral"),
    M(4L, "MEI", "Meia"),
    F(5L, "ATA", "Atacante"),
    V(8L, "", "Vazio"),
    N(9L, null, "Nulo");

    private final Long id;
    private final String codigo;
    private final String descricao;
}
