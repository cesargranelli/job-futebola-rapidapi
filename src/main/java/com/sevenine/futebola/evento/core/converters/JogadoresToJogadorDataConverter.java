package com.sevenine.futebola.evento.core.converters;

import com.sevenine.futebola.evento.adapter.repositories.data.FornecedorData;
import com.sevenine.futebola.evento.adapter.repositories.data.JogadorData;
import com.sevenine.futebola.evento.domain.entities.Jogadores;
import com.sevenine.futebola.evento.domain.enumerates.Fornecedor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class JogadoresToJogadorDataConverter implements Converter<Jogadores, JogadorData> {

    @Override
    public JogadorData convert(Jogadores source) {
        FornecedorData fornecedorData = new FornecedorData();
        fornecedorData.setId(Fornecedor.RAPIDAPI.getId());
        fornecedorData.setCodigo(Fornecedor.RAPIDAPI.getCodigo());
        fornecedorData.setNome(Fornecedor.RAPIDAPI.getNome());
        fornecedorData.setSituacao(Fornecedor.RAPIDAPI.isSituacao());

        JogadorData jogadorData = new JogadorData();
        jogadorData.setNomeCompleto(source.getNomeCompleto());
        jogadorData.setNomeCurto(source.getNomeCurto());
        jogadorData.setPosicao(source.getPosicao());
        jogadorData.setNumeroCamisa(source.getNumeroCamisa());
        jogadorData.setExternoId(source.getExternoId());
        jogadorData.setFornecedor(fornecedorData);
        return jogadorData;
    }

}
