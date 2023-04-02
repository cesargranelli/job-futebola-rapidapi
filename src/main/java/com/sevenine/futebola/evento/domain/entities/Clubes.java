package com.sevenine.futebola.evento.domain.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Clubes {
    private Long id;
    private String nomeCompleto;
    private String nomeCurto;
    private String nomeCodigo;
    private Long externoId;
}
