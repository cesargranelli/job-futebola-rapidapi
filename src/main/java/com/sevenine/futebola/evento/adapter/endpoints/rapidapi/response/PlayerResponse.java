package com.sevenine.futebola.evento.adapter.endpoints.rapidapi.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class PlayerResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -7238628118583200554L;
    private long id;
    private String name;
    private String shortName;
    private String position;
    private int shirtNumber;
}
