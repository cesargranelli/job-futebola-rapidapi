package com.sevenine.futebola.evento.domain.entities;

import java.time.LocalTime;
import java.util.List;

public class Parametros {

    public Parametros() {
    }

    private List<LocalTime> horarios;

    public List<LocalTime> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<LocalTime> horarios) {
        this.horarios = horarios;
    }

}
