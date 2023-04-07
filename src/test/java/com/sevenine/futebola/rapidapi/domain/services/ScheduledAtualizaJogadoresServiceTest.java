package com.sevenine.futebola.rapidapi.domain.services;

import com.sevenine.futebola.rapidapi.core.exceptions.ClubesNaoLocalizadosException;
import com.sevenine.futebola.rapidapi.core.exceptions.ConfiguracaoNaoLocalizadaException;
import com.sevenine.futebola.rapidapi.core.exceptions.HorarioJaExecucaoException;
import com.sevenine.futebola.rapidapi.core.exceptions.JogadoresNaoLocalizadosException;
import com.sevenine.futebola.rapidapi.core.properties.AppConfigJobProperties;
import com.sevenine.futebola.rapidapi.domain.entities.Clubes;
import com.sevenine.futebola.rapidapi.domain.entities.Jogadores;
import com.sevenine.futebola.rapidapi.domain.entities.Logs;
import com.sevenine.futebola.rapidapi.domain.entities.Parametros;
import com.sevenine.futebola.rapidapi.domain.ports.ConsultaClubesPort;
import com.sevenine.futebola.rapidapi.domain.ports.ConsultaJogadoresPort;
import com.sevenine.futebola.rapidapi.domain.ports.ConsultaLogsPort;
import com.sevenine.futebola.rapidapi.domain.ports.SalvaJogadoresPort;
import com.sevenine.futebola.rapidapi.domain.usecases.ConsultaConfiguracoes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduledAtualizaJogadoresServiceTest {

    @InjectMocks
    private ScheduledAtualizaJogadoresService service;

    @Mock
    private ConsultaConfiguracoes consultaConfiguracoes;

    @Mock
    private ConsultaLogsPort consultaLogs;

    @Mock
    private ConsultaClubesPort consultaClubes;

    @Mock
    private ConsultaJogadoresPort consultaJogadores;

    @Mock
    private SalvaJogadoresPort salvaJogadores;

    @Mock
    private AppConfigJobProperties jobProperties;

    @BeforeEach
    public void setUp() {
        when(jobProperties.getCodigoAtualizaJogadores()).thenReturn("codigo");
    }

    @Test
    public void naoDeveExecutarSemConfiguracaoParaCodigoInformado() {
        when(consultaConfiguracoes.consulta(anyString()))
                .thenThrow(ConfiguracaoNaoLocalizadaException.class);

        try {
            service.executa();
        } catch (ConfiguracaoNaoLocalizadaException e) {
            assertTrue(true);
        }

        verify(consultaLogs, never()).consulta(anyString(), any());
    }

    @Test
    public void naoDeveExecutarPorJaTerOcorridoExecucaoParaOHorario() {
        Parametros parametros = new Parametros();
        parametros.setHorarios(Collections.singletonList(LocalTime.of(20, 0, 0)));

        Logs logs = new Logs();
        logs.setDataHoraExecucao(LocalDate.now().atTime(LocalTime.of(20, 0, 0)));

        when(consultaConfiguracoes.consulta(anyString())).thenReturn(parametros);
        when(consultaLogs.consulta(anyString(), any())).thenReturn(Collections.singletonList(logs));

        try {
            service.executa();
        } catch (HorarioJaExecucaoException e) {
            assertTrue(true);
        }

        verify(consultaClubes, never()).consulta(anyLong());
    }

    @Test
    public void naoDeveExecutarBuscaDosJogadoresPorNaoTerClubesCadastrados() {
        Parametros parametros = new Parametros();
        parametros.setHorarios(Collections.singletonList(LocalTime.of(20, 0, 0)));

        Logs logs = new Logs();
        logs.setDataHoraExecucao(LocalDate.now().atTime(LocalTime.of(10, 0, 0)));

        when(consultaConfiguracoes.consulta(anyString())).thenReturn(parametros);
        when(consultaLogs.consulta(anyString(), any())).thenReturn(Collections.singletonList(logs));
        when(consultaClubes.consulta(anyLong())).thenReturn(Collections.emptyList());

        try {
            service.executa();
        } catch (ClubesNaoLocalizadosException e) {
            assertTrue(true);
        }

        verify(consultaJogadores, never()).consulta(anyList());
    }

    @Test
    public void naoDeveExecutarSalvamentoPorNaoTerJogadoresRetornadosNaConsulta() {
        Parametros parametros = new Parametros();
        parametros.setHorarios(Collections.singletonList(LocalTime.of(20, 0, 0)));

        Logs logs = new Logs();
        logs.setDataHoraExecucao(LocalDate.now().atTime(LocalTime.of(10, 0, 0)));

        Clubes clubes = new Clubes();
        clubes.setId(1L);

        when(consultaConfiguracoes.consulta(anyString())).thenReturn(parametros);
        when(consultaLogs.consulta(anyString(), any())).thenReturn(Collections.singletonList(logs));
        when(consultaClubes.consulta(anyLong())).thenReturn(Collections.singletonList(clubes));
        when(consultaJogadores.consulta(anyList())).thenReturn(Collections.emptyList());

        try {
            service.executa();
        } catch (JogadoresNaoLocalizadosException e) {
            assertTrue(true);
        }

        verify(salvaJogadores, never()).salva(anyList());
    }

    @Test
    public void deveExecutarAtualizacaoJogadores() {
        Parametros parametros = new Parametros();
        parametros.setHorarios(Collections.singletonList(LocalTime.of(20, 0, 0)));

        Logs logs = new Logs();
        logs.setDataHoraExecucao(LocalDate.now().atTime(LocalTime.of(10, 0, 0)));

        Clubes clubes = new Clubes();
        clubes.setId(1L);

        Jogadores jogadores = new Jogadores();
        jogadores.setId(1L);

        when(consultaConfiguracoes.consulta(anyString())).thenReturn(parametros);
        when(consultaLogs.consulta(anyString(), any())).thenReturn(Collections.singletonList(logs));
        when(consultaClubes.consulta(anyLong())).thenReturn(Collections.singletonList(clubes));
        when(consultaJogadores.consulta(anyList())).thenReturn(Collections.singletonList(jogadores));

        service.executa();

        verify(salvaJogadores, times(1)).salva(anyList());
    }

}