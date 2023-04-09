package com.sevenine.futebola.rapidapi.adapter.entrypoints.controller;

import com.sevenine.futebola.rapidapi.adapter.entrypoints.controller.apidoc.JogadoresApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("jogadores")
@RestController
public class JogadoresController implements JogadoresApi {

    @PutMapping
    @Override
    public ResponseEntity<Void> atualiza() {
        return null;
    }

}
