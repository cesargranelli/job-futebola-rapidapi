package com.sevenine.futebola.evento.domain.services;

import com.sevenine.futebola.evento.core.properties.AppConfigJobProperties;
import com.sevenine.futebola.evento.adapter.repositories.ClubeJpaRepository;
import com.sevenine.futebola.evento.adapter.repositories.JogadorJpaRepository;
import com.sevenine.futebola.evento.adapter.repositories.data.ClubeData;
import com.sevenine.futebola.evento.adapter.repositories.data.FornecedorData;
import com.sevenine.futebola.evento.adapter.repositories.data.JogadorData;
import com.sevenine.futebola.evento.adapter.endpoints.rapidapi.RapidApiFeignClient;
import com.sevenine.futebola.evento.adapter.endpoints.rapidapi.response.PlayerResponse;
import com.sevenine.futebola.evento.domain.entities.Logs;
import com.sevenine.futebola.evento.domain.entities.Parametros;
import com.sevenine.futebola.evento.core.exceptions.HorarioJaExecucaoException;
import com.sevenine.futebola.evento.domain.usecases.ConsultaConfiguracao;
import com.sevenine.futebola.evento.domain.usecases.ConsultaLog;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ScheduledAtualizaJogadoresService {

    private static final Logger log = LoggerFactory.getLogger(ScheduledAtualizaJogadoresService.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final AppConfigJobProperties jobProperties;

    private final ClubeJpaRepository clubeJpaRepository;
    private final RapidApiFeignClient rapidApiFeignClient;
    private final JogadorJpaRepository jogadorJpaRepository;

    private final ConsultaConfiguracao consultaConfiguracao;
    private final ConsultaLog consultaLog;

    //    @Scheduled(cron = "@hourly")
    @Scheduled(cron = "0/10 * * * * *")
    public void executa() {
        Parametros parametros = consultaConfiguracao.consulta(jobProperties.getCodigoAtualizaJogadores());

        List<Logs> logs = consultaLog.consulta(jobProperties.getCodigoAtualizaJogadores(), LocalDate.now());

        if (parametros.getHorarios().stream()
                .anyMatch(hora -> logs.stream()
                        .anyMatch(value -> value.getDataHoraExecucao().getHour() == hora.getHour())))
            throw new HorarioJaExecucaoException();

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
