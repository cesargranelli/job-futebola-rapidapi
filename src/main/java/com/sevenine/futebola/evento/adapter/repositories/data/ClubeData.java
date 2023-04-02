package com.sevenine.futebola.evento.adapter.repositories.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "clubes")
@Entity(name = "Clubes")
public class ClubeData {

    @Id
    private Long id;
    private String nomeCompleto;
    private String nomeCurto;
    private String nomeCodigo;
    private Long externoId;

}