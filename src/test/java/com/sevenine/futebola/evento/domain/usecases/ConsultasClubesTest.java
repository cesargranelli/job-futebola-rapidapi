package com.sevenine.futebola.evento.domain.usecases;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sevenine.futebola.evento.adapter.repositories.ClubeJpaRepository;
import com.sevenine.futebola.evento.adapter.repositories.data.ClubeData;
import com.sevenine.futebola.evento.domain.entities.Clubes;
import com.sevenine.futebola.evento.domain.entities.Logs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class ConsultasClubesTest {

    @InjectMocks
    private ConsultaClubes consultaClubes;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ClubeJpaRepository jpaRepository;

    @DisplayName("Deve retonar uma lista de clubes vazia")
    @Test
    void sucessoSemClubes() {
        when(jpaRepository.findByFornecedorId(anyLong())).thenReturn(Collections.emptyList());
        when(objectMapper.convertValue(anyCollection(), eq(List.class)))
                .thenReturn(Collections.emptyList());

        List<Clubes> clubesList = consultaClubes.consulta(1L);

        assertTrue("Objeto lista não vazio", clubesList.isEmpty());
    }

    @DisplayName("Deve retonar uma lista de clubes")
    @Test
    void sucessoComClubes() {
        when(jpaRepository.findByFornecedorId(anyLong()))
                .thenReturn(Collections.singletonList(new ClubeData()));
        when(objectMapper.convertValue(anyCollection(), eq(List.class)))
                .thenReturn(Collections.singletonList(new Logs()));

        List<Clubes> clubesList = consultaClubes.consulta(1L);

        assertTrue("Objeto lista retornou menos de 1 item", clubesList.size() == 1);
    }

}