package com.sevenine.futebola.evento.adapter.repositories.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Table(name = "configuracoes")
@Entity(name = "Configuracao")
public class ConfiguracaoData {

    @Id
    private Long id;
    private String tipo;
    private String codigo;
    private String parametros;
    private int versao;
    private LocalDate dataVersao;

}
