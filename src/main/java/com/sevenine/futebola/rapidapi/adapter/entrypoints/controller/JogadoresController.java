package com.sevenine.futebola.rapidapi.adapter.entrypoints.controller;

import com.sevenine.futebola.rapidapi.adapter.entrypoints.controller.apidoc.JogadoresApi;
import com.sevenine.futebola.rapidapi.domain.services.AtualizaJogadoresService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("jogadores")
@RestController
public class JogadoresController implements JogadoresApi {

    private final AtualizaJogadoresService service;

    @PutMapping
    @Override
    public ResponseEntity<Void> atualiza() {
        service.executa();

        return ResponseEntity.noContent().build();
    }

}
