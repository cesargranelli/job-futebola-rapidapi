package com.sevenine.futebola.evento.domain.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Logs {
    private Long id;
    private String codigoConfiguracao;
    private LocalDateTime dataHoraExecucao;
    private String observacao;
}
