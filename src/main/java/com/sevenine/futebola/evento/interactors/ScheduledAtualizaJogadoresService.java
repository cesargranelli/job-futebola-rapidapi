package com.sevenine.futebola.evento.interactors;

import com.sevenine.futebola.evento.configs.properties.AppConfigExceptionProperties;
import com.sevenine.futebola.evento.configs.properties.AppConfigJobProperties;
import com.sevenine.futebola.evento.datasources.database.ClubeJpaRepository;
import com.sevenine.futebola.evento.datasources.database.ConfiguracaoJpaRepository;
import com.sevenine.futebola.evento.datasources.database.JogadorJpaRepository;
import com.sevenine.futebola.evento.datasources.database.LogJpaRepository;
import com.sevenine.futebola.evento.datasources.database.data.ClubeData;
import com.sevenine.futebola.evento.datasources.database.data.ConfiguracaoData;
import com.sevenine.futebola.evento.datasources.database.data.FornecedorData;
import com.sevenine.futebola.evento.datasources.database.data.JogadorData;
import com.sevenine.futebola.evento.datasources.restapi.rapidapi.RapidApiFeignClient;
import com.sevenine.futebola.evento.datasources.restapi.rapidapi.response.PlayerResponse;
import com.sevenine.futebola.evento.entities.Parametros;
import com.sevenine.futebola.evento.handlers.exceptions.ExecucaoJobException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ScheduledAtualizaJogadoresService {

    private static final Logger log = LoggerFactory.getLogger(ScheduledAtualizaJogadoresService.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final AppConfigJobProperties jobProperties;
    private final AppConfigExceptionProperties exceptionProperties;

    private final ConfiguracaoJpaRepository configuracaoJpaRepository;
    private final LogJpaRepository logJpaRepository;
    private final ClubeJpaRepository clubeJpaRepository;
    private final RapidApiFeignClient rapidApiFeignClient;
    private final JogadorJpaRepository jogadorJpaRepository;

    //    @Scheduled(cron = "@hourly")
    @Scheduled(cron = "0/10 * * * * *")
    public void execute() {
        Optional<ConfiguracaoData> optional =
                configuracaoJpaRepository.findByCodigo(jobProperties.getCodigoAtualizaJogadores());

        if (optional.isEmpty()) throw new ExecucaoJobException(
                exceptionProperties.getCodigoNaoConfigurado().getCodigo(),
                exceptionProperties.getCodigoNaoConfigurado().getDescricao());

        Parametros parametros = new Parametros(optional.orElseThrow().getParametros());

        boolean contain = parametros.getHorarios()
                .contains(LocalTime.now());

        if (!contain) return;

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
