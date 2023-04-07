package com.sevenine.futebola.evento.domain.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class Clubes implements Serializable {
    @Serial
    private static final long serialVersionUID = -7379298383805016565L;
    private Long id;
    private String nomeCompleto;
    private String nomeCurto;
    private String nomeCodigo;
    private Long externoId;
    private Long fornecedorId;
}
