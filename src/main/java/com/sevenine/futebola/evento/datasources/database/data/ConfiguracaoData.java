package com.sevenine.futebola.evento.datasources.database.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Table(name = "configuracoes")
@Entity
public class ConfiguracaoData {

    @Id
    private Long id;
    private String tipo;
    private String parametros;
    private int versao;
    private LocalDate dataVersao;

    public String getParametros() {
        return parametros;
    }
}
