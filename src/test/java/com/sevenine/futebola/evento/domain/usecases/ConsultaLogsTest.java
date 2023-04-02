package com.sevenine.futebola.evento.domain.usecases;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sevenine.futebola.evento.adapter.repositories.LogJpaRepository;
import com.sevenine.futebola.evento.adapter.repositories.data.LogData;
import com.sevenine.futebola.evento.domain.entities.Logs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class ConsultaLogsTest {

    @InjectMocks
    private ConsultaLog consultaLog;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private LogJpaRepository jpaRepository;

    @DisplayName("Deve retonar uma lista de logs vazia")
    @Test
    void sucessoSemLogs() {
        when(jpaRepository.findByCodigoConfiguracaoDataHoraExecucaoBetween(anyString(), any(), any()))
                .thenReturn(Collections.emptyList());
        when(objectMapper.convertValue(anyCollection(), eq(List.class)))
                .thenReturn(Collections.emptyList());

        List<Logs> logsList = consultaLog.consulta("codigoParametros", LocalDate.now());

        assertTrue("Objeto lista não vazio", logsList.isEmpty());
    }

    @DisplayName("Retornando um único item na lista de logs")
    @Test
    void sucessoComUmItemNaLista() {
        when(jpaRepository.findByCodigoConfiguracaoDataHoraExecucaoBetween(anyString(), any(), any()))
                .thenReturn(Collections.singletonList(new LogData()));
        when(objectMapper.convertValue(anyCollection(), eq(List.class)))
                .thenReturn(Collections.singletonList(new Logs()));

        List<Logs> logsList = consultaLog.consulta("codigoParametros", LocalDate.now());

        assertTrue("Objeto lista retornou mais de um único item", logsList.size() == 1);
    }

    @DisplayName("Retornando mais de um item na lista de logs")
    @Test
    void sucessoComDoisItensNaLista() {
        when(jpaRepository.findByCodigoConfiguracaoDataHoraExecucaoBetween(anyString(), any(), any()))
                .thenReturn(Arrays.asList(new LogData(), new LogData()));
        when(objectMapper.convertValue(anyCollection(), eq(List.class)))
                .thenReturn(Arrays.asList(new Logs(), new Logs()));

        List<Logs> logsList = consultaLog.consulta("codigoParametros", LocalDate.now());

        assertTrue("Objeto lista retornou menos do que 2 itens", logsList.size() >= 2);
    }

}