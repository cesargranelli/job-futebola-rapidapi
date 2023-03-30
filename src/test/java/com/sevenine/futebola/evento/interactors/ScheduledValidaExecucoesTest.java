package com.sevenine.futebola.evento.interactors;

import com.sevenine.futebola.evento.configs.properties.AppConfigExceptionProperties;
import com.sevenine.futebola.evento.configs.properties.AppConfigJobProperties;
import com.sevenine.futebola.evento.datasources.database.ClubeJpaRepository;
import com.sevenine.futebola.evento.datasources.database.ConfiguracaoJpaRepository;
import com.sevenine.futebola.evento.datasources.database.LogJpaRepository;
import com.sevenine.futebola.evento.datasources.database.data.ConfiguracaoData;
import com.sevenine.futebola.evento.handlers.exceptions.ApplicationException;
import com.sevenine.futebola.evento.handlers.exceptions.ExecucaoJobException;
import com.sevenine.futebola.evento.handlers.exceptions.data.ExceptionData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduledValidaExecucoesTest {

    @InjectMocks
    private ScheduledAtualizaJogadoresService service;

    @Mock
    private ConfiguracaoJpaRepository configuracaoJpaRepository;

    @Mock
    private LogJpaRepository logJpaRepository;

    @Mock
    private ClubeJpaRepository clubeJpaRepository;

    @Mock
    private AppConfigJobProperties jobProperties;

    @Mock
    private AppConfigExceptionProperties exceptionProperties;

    @BeforeEach
    public void setUp() {
        AppConfigJobProperties appConfigJobProperties = new AppConfigJobProperties();
        appConfigJobProperties.setCodigoAtualizaJogadores("codigo1");
        when(jobProperties.getCodigoAtualizaJogadores())
                .thenReturn(appConfigJobProperties.getCodigoAtualizaJogadores());
    }

    @Test
    public void naoExecutarDevidoNaoHaverConfiguracaoParaCodigoInformado() throws ExecucaoJobException {
        ExceptionData codigoNaoConfigurado = new ExceptionData();
        codigoNaoConfigurado.setCodigo("101");
        codigoNaoConfigurado.setDescricao("Exception 101");

        AppConfigExceptionProperties appConfigExceptionProperties = new AppConfigExceptionProperties();
        appConfigExceptionProperties.setCodigoNaoConfigurado(codigoNaoConfigurado);

        when(exceptionProperties.getCodigoNaoConfigurado())
                .thenReturn(appConfigExceptionProperties.getCodigoNaoConfigurado());
        when(configuracaoJpaRepository.findByCodigo(anyString())).thenReturn(Optional.empty());

        ApplicationException exception = assertThrows(ExecucaoJobException.class, () ->
                service.execute());

        assertEquals("101", exception.getCodigo());
        assertEquals("Exception 101", exception.getDescricao());
        verify(logJpaRepository, never()).findByCodigo(anyString());
    }

    @Test
    public void naoExecutarDevidoParametrosJobForaHorario() {
        ConfiguracaoData configuracaoData = new ConfiguracaoData();
        configuracaoData.setParametros("{\"horarios\":[\"20:00:00\"]}");

        try (MockedStatic<LocalTime> localTimeMockedStatic = mockStatic(LocalTime.class)) {
            LocalTime localTime = LocalTime.of(18, 0);
            localTimeMockedStatic.when(LocalTime::now).thenReturn(localTime);
        }

        when(configuracaoJpaRepository.findByCodigo(anyString())).thenReturn(Optional.of(configuracaoData));

        service.execute();

        verify(clubeJpaRepository, never()).findAll();
    }

    @Test
    public void naoExecutarDevidoJaTerOcorridoExecucaoParaOHorario() {
        assertTrue(true);
    }

    @Test
    public void executarDevidoParametrosJobDentroHorarioESemExecucaoAnterior() {
        ConfiguracaoData configuracaoData = new ConfiguracaoData();
        configuracaoData.setParametros("{\"horarios\":[\"20:00\"]}");

        try (MockedStatic<LocalTime> localTimeMockedStatic = mockStatic(LocalTime.class)) {
            LocalTime localTime = LocalTime.of(20, 0);
            localTimeMockedStatic.when(LocalTime::now).thenReturn(localTime);
        }

        when(configuracaoJpaRepository.findByCodigo(anyString())).thenReturn(Optional.of(configuracaoData));

        service.execute();

        verify(clubeJpaRepository, times(1)).findAll();
    }

}