package com.sevenine.futebola.evento.domain.usecases;

import com.sevenine.futebola.evento.adapter.repositories.JogadorJpaRepository;
import com.sevenine.futebola.evento.adapter.repositories.data.JogadorData;
import com.sevenine.futebola.evento.core.converters.JogadoresToJogadorDataConverter;
import com.sevenine.futebola.evento.domain.entities.Jogadores;
import com.sevenine.futebola.evento.domain.enumerates.Fornecedor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class SalvaJogadoresTest {

    @InjectMocks
    private SalvaJogadores salvaJogadores;

    @Mock
    private JogadorJpaRepository jpaRepository;

    @Mock
    private JogadoresToJogadorDataConverter converter;

    @DisplayName("Deve salvar apenas jogadores que ainda não estão na base")
    @Test
    void deveIgnorarObjetoJogadorVazio() {
        List<Jogadores> jogadoresList = criaJogadores();

        when(jpaRepository.findByExternoId(anyLong())).thenReturn(Optional.of(new JogadorData())).thenReturn(Optional.empty());
        when(converter.convert(any())).thenReturn(new JogadorData());
        when(jpaRepository.save(any())).thenReturn(new JogadorData());

        salvaJogadores.salva(jogadoresList);

        verify(jpaRepository, times(1)).save(any(JogadorData.class));
    }

    private List<Jogadores> criaJogadores() {
        Jogadores jogadores1 = new Jogadores();
        jogadores1.setNomeCompleto("Jogador 1");
        jogadores1.setNomeCurto("Jogador 1");
        jogadores1.setNumeroCamisa(1);
        jogadores1.setPosicao("G");
        jogadores1.setExternoId(1L);
        jogadores1.setFornecedor(Fornecedor.RAPIDAPI);

        Jogadores jogadores2 = new Jogadores();
        jogadores2.setNomeCompleto("Jogador 2");
        jogadores2.setNomeCurto("Jogador 2");
        jogadores2.setNumeroCamisa(2);
        jogadores2.setPosicao("D");
        jogadores2.setExternoId(2L);
        jogadores2.setFornecedor(Fornecedor.RAPIDAPI);

        return Arrays.asList(jogadores1, jogadores2);
    }

}