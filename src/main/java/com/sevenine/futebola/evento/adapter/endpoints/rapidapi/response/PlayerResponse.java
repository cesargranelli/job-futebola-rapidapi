package com.sevenine.futebola.evento.adapter.endpoints.rapidapi.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerResponse {
    private long id;
    private String name;
    private String shortName;
    private String position;
    private int shirtNumber;
}