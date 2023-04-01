package com.sevenine.futebola.evento.datasources.database.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "logs")
@Entity(name = "Logs")
public class LogData {

    @Id
    private Long id;
    private String codigoConfiguracao;
    private LocalDateTime dataHoraExecucao;
    private String observacao;

}
