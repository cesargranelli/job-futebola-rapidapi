package com.sevenine.futebola.rapidapi.domain.services;

import com.sevenine.futebola.rapidapi.core.exceptions.HorarioJaExecucaoException;
import com.sevenine.futebola.rapidapi.core.properties.AppConfigJobProperties;
import com.sevenine.futebola.rapidapi.domain.entities.Clubes;
import com.sevenine.futebola.rapidapi.domain.entities.Jogadores;
import com.sevenine.futebola.rapidapi.domain.entities.Logs;
import com.sevenine.futebola.rapidapi.domain.entities.Parametros;
import com.sevenine.futebola.rapidapi.domain.enumerates.Fornecedor;
import com.sevenine.futebola.rapidapi.domain.ports.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ScheduledAtualizaJogadoresService {

    private final AppConfigJobProperties jobProperties;
    private final ConsultaConfiguracoesPort consultaConfiguracoes;
    private final ConsultaLogsPort consultaLogs;
    private final ConsultaClubesPort consultaClubes;
    private final ConsultaJogadoresPort consultaJogadores;
    private final SalvaJogadoresPort salvaJogadores;

    //    @Scheduled(cron = "@hourly")
    @Scheduled(cron = "0/10 * * * * *")
    public void executa() {
        Parametros parametros = consultaConfiguracoes.consulta(jobProperties.getCodigoAtualizaJogadores());

        List<Logs> logs = consultaLogs.consulta(jobProperties.getCodigoAtualizaJogadores(), LocalDate.now());

        if (parametros.getHorarios().stream()
                .anyMatch(hora -> logs.stream()
                        .anyMatch(value -> value.getDataHoraExecucao().getHour() == hora.getHour())))
            throw new HorarioJaExecucaoException();

        List<Clubes> clubes = consultaClubes.consulta(Fornecedor.RAPIDAPI.getId());

        List<Jogadores> jogadores = consultaJogadores.consulta(clubes);

        salvaJogadores.salva(jogadores);
    }

}
