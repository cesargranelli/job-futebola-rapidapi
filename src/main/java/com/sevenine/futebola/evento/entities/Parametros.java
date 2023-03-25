package com.sevenine.futebola.evento.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.json.BasicJsonParser;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Parametros {
    private List<LocalTime> horarios;

    public Parametros(String parametros) {
        BasicJsonParser basicJsonParser = new BasicJsonParser();
        Map<String, Object> objectMap = basicJsonParser.parseMap(parametros);
        horarios = (List<LocalTime>) objectMap.get("horarios");
    }
}
