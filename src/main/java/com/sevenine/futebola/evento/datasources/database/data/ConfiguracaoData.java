package com.sevenine.futebola.evento.datasources.database.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Table(name = "configuracoes")
@Entity(name = "Configuracoes")
public class ConfiguracaoData {

    @Id
    private Long id;
    private String tipo;
    private String codigo;
    private String parametros;
    private int versao;
    private LocalDate dataVersao;

}
