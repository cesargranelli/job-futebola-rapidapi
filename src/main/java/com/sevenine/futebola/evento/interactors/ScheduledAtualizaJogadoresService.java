package com.sevenine.futebola.evento.interactors;

import com.sevenine.futebola.evento.datasources.database.ClubeJpaRepository;
import com.sevenine.futebola.evento.datasources.database.ConfiguracaoJpaRepository;
import com.sevenine.futebola.evento.datasources.database.data.ClubeData;
import com.sevenine.futebola.evento.datasources.database.data.ConfiguracaoData;
import com.sevenine.futebola.evento.datasources.restapi.rapidapi.RapidApiFeignClient;
import com.sevenine.futebola.evento.datasources.restapi.rapidapi.response.PlayerResponse;
import com.sevenine.futebola.evento.entities.Parametros;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

@Service
public class ScheduledAtualizaJogadoresService {

    private static final Logger log = LoggerFactory.getLogger(ScheduledAtualizaJogadoresService.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private ConfiguracaoJpaRepository configuracaoJpaRepository;

    @Autowired
    private ClubeJpaRepository clubeJpaRepository;

    @Autowired
    private RapidApiFeignClient rapidApiFeignClient;

    //    @Scheduled(cron = "@hourly")
    @Scheduled(cron = "0/10 * * * * *")
    public void execute() {
        Optional<ConfiguracaoData> optional =
                configuracaoJpaRepository.findByCodigo("1ff2df54-caa1-11ed-8305-a251453ec86a");

        Parametros parametros = new Parametros(optional.orElseThrow().getParametros());

        Boolean contain = parametros.getHorarios().contains(String.valueOf(LocalTime.of(20, 0, 0)));

        log.info(String.valueOf(contain));
        log.info(String.valueOf(LocalTime.of(20, 0, 0)));

        List<ClubeData> clubes = clubeJpaRepository.findAll();

        Map<String, List<Map<String, PlayerResponse>>> players = rapidApiFeignClient.getPlayers(1973L);

        log.info("The time is now {}", dateFormat.format(new Date()));
    }

}
