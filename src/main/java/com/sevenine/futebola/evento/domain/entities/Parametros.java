package com.sevenine.futebola.evento.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Parametros {
    private List<LocalTime> horarios;
}
