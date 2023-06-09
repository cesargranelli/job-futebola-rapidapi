package com.sevenine.futebola.rapidapi.domain.usecases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.sevenine.futebola.rapidapi.adapter.repositories.ConfiguracaoJpaRepository;
import com.sevenine.futebola.rapidapi.adapter.repositories.data.ConfiguracaoData;
import com.sevenine.futebola.rapidapi.domain.entities.Parametros;
import com.sevenine.futebola.rapidapi.core.exceptions.ApplicationException;
import com.sevenine.futebola.rapidapi.core.exceptions.ConfiguracaoNaoLocalizadaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class ConsultaConfiguracoesTest {

    @InjectMocks
    private ConsultaConfiguracoes consultaConfiguracoes;

    @Mock
    private JsonMapper jsonMapper;

    @Mock
    private ConfiguracaoJpaRepository jpaRepository;

    private Parametros parametros;

    @BeforeEach
    void setUp() {
        parametros = new Parametros();
        parametros.setHorarios(Collections.singletonList(LocalTime.of(20, 0, 0)));
    }

    @DisplayName("Deve retonar os parâmetros localizados na base")
    @Test
    void sucesso() throws JsonProcessingException {
        ConfiguracaoData configuracaoData = new ConfiguracaoData();
        configuracaoData.setParametros("{\"horarios\":[\"20:00:00\"]}");

        when(jsonMapper.readValue(anyString(), eq(Parametros.class))).thenReturn(parametros);
        when(jpaRepository.findByCodigo(anyString())).thenReturn(Optional.of(configuracaoData));

        Parametros parametrosConsulta = consultaConfiguracoes.consulta("codigoParametros");

        int hour1 = parametros.getHorarios().get(0).getHour();
        int hour2 = parametrosConsulta.getHorarios().get(0).getHour();
        assertTrue("Divergência na validação dos parâmetros", hour1 == hour2);
    }

    @DisplayName("Deve retonar exceção de parâmetros não localizados")
    @Test
    void parametrosNaoLocalizados() throws JsonProcessingException {
        when(jsonMapper.readValue(anyString(), eq(Parametros.class))).thenReturn(parametros);
        when(jpaRepository.findByCodigo(anyString())).thenReturn(Optional.empty());

        ApplicationException exception = assertThrows(ConfiguracaoNaoLocalizadaException.class, () ->
                consultaConfiguracoes.consulta("codigoParametros"));

        assertTrue("Objeto de tipo diferente do esperado",
                exception.getClass().getName().contains("ConfiguracaoNaoLocalizadaException"));
    }

    @DisplayName("Deve retonar exceção por falha na conversão do JSON")
    @Test
    void falhaConversaoJson() throws JsonProcessingException {
        ConfiguracaoData configuracaoData = new ConfiguracaoData();
        configuracaoData.setParametros("{\"horarios\":[\"20:00:00\"]}");

        when(jsonMapper.readValue(anyString(), eq(Parametros.class))).thenThrow(JsonProcessingException.class);
        when(jpaRepository.findByCodigo(anyString())).thenReturn(Optional.of(configuracaoData));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                consultaConfiguracoes.consulta("codigoParametros"));

        assertTrue("Objeto de tipo diferente do esperado",
                exception.getClass().getName().contains("RuntimeException"));
    }

}