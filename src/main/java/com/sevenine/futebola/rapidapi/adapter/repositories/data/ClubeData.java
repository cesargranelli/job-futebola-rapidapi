package com.sevenine.futebola.rapidapi.adapter.repositories.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Table(name = "clubes")
@Entity(name = "Clube")
public class ClubeData implements Serializable {

    @Serial
    private static final long serialVersionUID = 6586047789987827582L;

    @Id
    private Long id;
    private String nomeCompleto;
    private String nomeCurto;
    private String nomeCodigo;
    private Long externoId;
    private Long fornecedorId;

}
