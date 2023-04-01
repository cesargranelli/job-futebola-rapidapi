package com.sevenine.futebola.evento.entities;

import java.time.LocalDateTime;

public class Logs {

    private Long id;
    private String codigoConfiguracao;
    private LocalDateTime dataHoraExecucao;
    private String observacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoConfiguracao() {
        return codigoConfiguracao;
    }

    public void setCodigoConfiguracao(String codigoConfiguracao) {
        this.codigoConfiguracao = codigoConfiguracao;
    }

    public LocalDateTime getDataHoraExecucao() {
        return dataHoraExecucao;
    }

    public void setDataHoraExecucao(LocalDateTime dataHoraExecucao) {
        this.dataHoraExecucao = dataHoraExecucao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

}
