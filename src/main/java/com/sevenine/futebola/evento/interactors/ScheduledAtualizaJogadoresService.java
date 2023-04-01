package com.sevenine.futebola.evento.interactors;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.sevenine.futebola.evento.configs.properties.AppConfigExceptionProperties;
import com.sevenine.futebola.evento.configs.properties.AppConfigJobProperties;
import com.sevenine.futebola.evento.datasources.database.ClubeJpaRepository;
import com.sevenine.futebola.evento.datasources.database.ConfiguracaoJpaRepository;
import com.sevenine.futebola.evento.datasources.database.JogadorJpaRepository;
import com.sevenine.futebola.evento.datasources.database.LogJpaRepository;
import com.sevenine.futebola.evento.datasources.database.data.*;
import com.sevenine.futebola.evento.datasources.restapi.rapidapi.RapidApiFeignClient;
import com.sevenine.futebola.evento.datasources.restapi.rapidapi.response.PlayerResponse;
import com.sevenine.futebola.evento.entities.Parametros;
import com.sevenine.futebola.evento.handlers.exceptions.ExecucaoJobException;
import com.sevenine.futebola.evento.interactors.usecase.ConsultaConfiguracao;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ScheduledAtualizaJogadoresService {

    private static final Logger log = LoggerFactory.getLogger(ScheduledAtualizaJogadoresService.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final JsonMapper jsonMapper;

    private final AppConfigJobProperties jobProperties;
    private final AppConfigExceptionProperties exceptionProperties;

    private final ConfiguracaoJpaRepository configuracaoJpaRepository;
    private final LogJpaRepository logJpaRepository;
    private final ClubeJpaRepository clubeJpaRepository;
    private final RapidApiFeignClient rapidApiFeignClient;
    private final JogadorJpaRepository jogadorJpaRepository;

    private final ConsultaConfiguracao consultaConfiguracao;

    //    @Scheduled(cron = "@hourly")
    @Scheduled(cron = "0/10 * * * * *")
    public void executa() {
        Parametros parametros = consultaConfiguracao.consulta(jobProperties.getCodigoAtualizaJogadores());

        Optional<ConfiguracaoData> optionalConfiguracao =
                configuracaoJpaRepository.findByCodigo(jobProperties.getCodigoAtualizaJogadores());

        if (optionalConfiguracao.isEmpty()) throw new ExecucaoJobException(
                exceptionProperties.getCodigoNaoConfigurado().getCodigo(),
                exceptionProperties.getCodigoNaoConfigurado().getDescricao());

        Optional<LogData> optionalLog =
                logJpaRepository.findByCodigoConfiguracao(jobProperties.getCodigoAtualizaJogadores());

        if (optionalLog.isEmpty()) throw new ExecucaoJobException(
                exceptionProperties.getForaDoHorarioDeExecucao().getCodigo(),
                exceptionProperties.getForaDoHorarioDeExecucao().getDescricao());

        boolean contain = parametros.getHorarios().stream()
                .anyMatch(hora -> hora.getHour() == optionalLog.orElseThrow().getDataHoraExecucao().getHour());

        if (contain) throw new ExecucaoJobException(
                exceptionProperties.getHorarioJaExecutado().getCodigo(),
                exceptionProperties.getHorarioJaExecutado().getDescricao());

        List<ClubeData> clubes = clubeJpaRepository.findAll();

        Map<String, List<Map<String, PlayerResponse>>> players = rapidApiFeignClient.getPlayers(1973L);

        List<JogadorData> jogadores = new ArrayList<>(players.get("players").stream().map(player -> {
            JogadorData jogadorData = new JogadorData();

            FornecedorData fornecedorData = new FornecedorData();
            fornecedorData.setId(1L);
            fornecedorData.setCodigo("83d49e34-cabb-11ed-8305-a251453ec86a");
            fornecedorData.setNome("rapidapi");
            fornecedorData.setSituacao(true);

            jogadorData.setFornecedor(fornecedorData);
            jogadorData.setExternoId(player.get("player").getId());
            jogadorData.setNomeCompleto(player.get("player").getName());
            jogadorData.setNomeCurto(player.get("player").getShortName());
            jogadorData.setPosicao(player.get("player").getPosition());
            jogadorData.setNumeroCamisa(player.get("player").getShirtNumber());

            return jogadorData;
        }).toList());

        jogadorJpaRepository.saveAll(jogadores);

        log.info("The time is now {}", dateFormat.format(new Date()));
    }

}
