package com.sevenine.futebola.rapidapi.adapter.entrypoints.controller.apidoc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface JogadoresApi {

    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> atualiza();

}
