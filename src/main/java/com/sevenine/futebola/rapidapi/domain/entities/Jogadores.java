package com.sevenine.futebola.rapidapi.domain.entities;

import com.sevenine.futebola.rapidapi.domain.enumerates.Fornecedor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Jogadores {
    private Long id;
    private String nomeCompleto;
    private String nomeCurto;
    private String posicao;
    private Integer numeroCamisa;
    private Long externoId;
    private Fornecedor fornecedor;
}
