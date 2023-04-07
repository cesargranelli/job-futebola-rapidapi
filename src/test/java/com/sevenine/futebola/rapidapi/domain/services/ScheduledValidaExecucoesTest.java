package com.sevenine.futebola.evento.domain.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.sevenine.futebola.evento.core.properties.AppConfigExceptionProperties;
import com.sevenine.futebola.evento.core.properties.AppConfigJobProperties;
import com.sevenine.futebola.evento.adapter.repositories.ClubeJpaRepository;
import com.sevenine.futebola.evento.adapter.repositories.ConfiguracaoJpaRepository;
import com.sevenine.futebola.evento.adapter.repositories.JogadorJpaRepository;
import com.sevenine.futebola.evento.adapter.repositories.LogJpaRepository;
import com.sevenine.futebola.evento.adapter.repositories.data.ClubeData;
import com.sevenine.futebola.evento.adapter.repositories.data.ConfiguracaoData;
import com.sevenine.futebola.evento.adapter.repositories.data.LogData;
import com.sevenine.futebola.evento.domain.entities.Parametros;
import com.sevenine.futebola.evento.core.exceptions.ApplicationException;
import com.sevenine.futebola.evento.core.exceptions.ExecucaoJobException;
import com.sevenine.futebola.evento.core.exceptions.data.ExceptionData;
import com.sevenine.futebola.evento.domain.ports.ConsultaConfiguracoesPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduledValidaExecucoesTest {

    @InjectMocks
    private ScheduledAtualizaJogadoresService service;

    @Mock
    private ConsultaConfiguracoesPort consultaConfiguracao;

    @Mock
    private ConfiguracaoJpaRepository configuracaoJpaRepository;

    @Mock
    private LogJpaRepository logJpaRepository;

    @Mock
    private ClubeJpaRepository clubeJpaRepository;

    @Mock
    private JogadorJpaRepository jogadorJpaRepository;

    @Mock
    private AppConfigJobProperties jobProperties;

    @Mock
    private AppConfigExceptionProperties exceptionProperties;

    @Mock
    private JsonMapper jsonMapper;

    @BeforeEach
    public void setUp() {
        AppConfigJobProperties appConfigJobProperties = new AppConfigJobProperties();
        appConfigJobProperties.setCodigoAtualizaJogadores("codigo1");
        when(jobProperties.getCodigoAtualizaJogadores())
                .thenReturn(appConfigJobProperties.getCodigoAtualizaJogadores());
    }

    @Test
    public void naoDeveExecutarSemConfiguracaoParaCodigoInformado() throws ExecucaoJobException {
        ExceptionData codigoNaoConfigurado = new ExceptionData();
        codigoNaoConfigurado.setCodigo("101");
        codigoNaoConfigurado.setDescricao("Exception 101");

        AppConfigExceptionProperties appConfigExceptionProperties = new AppConfigExceptionProperties();
        appConfigExceptionProperties.setCodigoNaoConfigurado(codigoNaoConfigurado);

        when(exceptionProperties.getCodigoNaoConfigurado())
                .thenReturn(appConfigExceptionProperties.getCodigoNaoConfigurado());
        when(configuracaoJpaRepository.findByCodigo(anyString())).thenReturn(Optional.empty());

        ApplicationException exception = assertThrows(ExecucaoJobException.class, () ->
                service.executa());

        assertEquals("101", exception.getCodigo());
        assertEquals("Exception 101", exception.getDescricao());
//        verify(logJpaRepository, never()).findByCodigoConfiguracaoDataHoraExecucaoBetween(anyString(), dataExecucao);
    }

    @Test
    public void naoDeveExecutarComParametrosJobForaHorario() {
        ExceptionData foraDoHorarioDeExecucao = new ExceptionData();
        foraDoHorarioDeExecucao.setCodigo("102");
        foraDoHorarioDeExecucao.setDescricao("Exception 102");

        AppConfigExceptionProperties appConfigExceptionProperties = new AppConfigExceptionProperties();
        appConfigExceptionProperties.setForaDoHorarioDeExecucao(foraDoHorarioDeExecucao);

        when(configuracaoJpaRepository.findByCodigo(anyString())).thenReturn(Optional.of(new ConfiguracaoData()));
        when(exceptionProperties.getForaDoHorarioDeExecucao())
                .thenReturn(appConfigExceptionProperties.getForaDoHorarioDeExecucao());
//        when(logJpaRepository.findByCodigoConfiguracaoDataHoraExecucaoBetween(anyString(), dataExecucao)).thenReturn(Optional.empty());

        ApplicationException exception = assertThrows(ExecucaoJobException.class, () ->
                service.executa());

        assertEquals("102", exception.getCodigo());
        assertEquals("Exception 102", exception.getDescricao());
        verify(clubeJpaRepository, never()).findAll();
    }

    @Test
    public void naoDeveExecutarPorJaTerOcorridoExecucaoParaOHorario() throws JsonProcessingException {
        ConfiguracaoData configuracaoData = new ConfiguracaoData();
        configuracaoData.setParametros("{\"horarios\":[\"20:00:00\"]}");

        LogData logData = new LogData();
        logData.setDataHoraExecucao(LocalDateTime.of(LocalDate.now(), LocalTime.of(20, 0, 0)));

        ExceptionData horarioJaExecutado = new ExceptionData();
        horarioJaExecutado.setCodigo("103");
        horarioJaExecutado.setDescricao("Exception 103");

        AppConfigExceptionProperties appConfigExceptionProperties = new AppConfigExceptionProperties();
        appConfigExceptionProperties.setHorarioJaExecutado(horarioJaExecutado);

        Parametros parametros = new Parametros();
        parametros.setHorarios(Collections.singletonList(LocalTime.of(20, 0, 0)));

        when(configuracaoJpaRepository.findByCodigo(anyString())).thenReturn(Optional.of(configuracaoData));
        when(exceptionProperties.getHorarioJaExecutado()).thenReturn(appConfigExceptionProperties.getHorarioJaExecutado());
//        when(logJpaRepository.findByCodigoConfiguracaoDataHoraExecucaoBetween(anyString(), dataExecucao)).thenReturn(Optional.of(logData));
        when(jsonMapper.readValue(anyString(), eq(Parametros.class))).thenReturn(parametros);

        ApplicationException exception = assertThrows(ExecucaoJobException.class, () ->
                service.executa());

        assertEquals("103", exception.getCodigo());
        assertEquals("Exception 103", exception.getDescricao());
        assertTrue(parametros.getHorarios().stream()
                .anyMatch(hora -> hora.getHour() == logData.getDataHoraExecucao().getHour()));
        verify(jogadorJpaRepository, never()).saveAll(any());
    }

    @Test
    public void deveExecutarProcessoComSucesso() throws JsonProcessingException {
        ConfiguracaoData configuracaoData = new ConfiguracaoData();
        configuracaoData.setParametros("{\"horarios\":[\"20:00:00\"]}");

        Parametros parametros = new Parametros();
        parametros.setHorarios(Collections.singletonList(LocalTime.of(21, 0, 0)));

        when(configuracaoJpaRepository.findByCodigo(anyString())).thenReturn(Optional.of(configuracaoData));
//        when(logJpaRepository.findByCodigoConfiguracaoDataHoraExecucaoBetween(anyString(), dataExecucao)).thenReturn(Optional.empty());
        when(jsonMapper.readValue(anyString(), eq(Parametros.class))).thenReturn(parametros);
        when(clubeJpaRepository.findAll()).thenReturn(Collections.singletonList(new ClubeData()));

        service.executa();

        verify(clubeJpaRepository, times(1)).findAll();
    }

}