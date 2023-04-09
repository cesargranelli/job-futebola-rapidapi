package com.sevenine.futebola.rapidapi.domain.services;

import com.sevenine.futebola.rapidapi.core.exceptions.ClubesNaoLocalizadosException;
import com.sevenine.futebola.rapidapi.core.exceptions.HorarioJaExecucaoException;
import com.sevenine.futebola.rapidapi.core.exceptions.JogadoresNaoLocalizadosException;
import com.sevenine.futebola.rapidapi.core.properties.AppConfigJobProperties;
import com.sevenine.futebola.rapidapi.domain.entities.Clubes;
import com.sevenine.futebola.rapidapi.domain.entities.Jogadores;
import com.sevenine.futebola.rapidapi.domain.entities.Logs;
import com.sevenine.futebola.rapidapi.domain.entities.Parametros;
import com.sevenine.futebola.rapidapi.domain.enumerates.Fornecedor;
import com.sevenine.futebola.rapidapi.domain.ports.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AtualizaJogadoresService {

    private final AppConfigJobProperties jobProperties;
    private final ConsultaConfiguracoesPort consultaConfiguracoes;
    private final ConsultaLogsPort consultaLogs;
    private final ConsultaClubesPort consultaClubes;
    private final ConsultaJogadoresPort consultaJogadores;
    private final SalvaJogadoresPort salvaJogadores;

    public void executa() {
        Parametros parametros = consultaConfiguracoes.consulta(jobProperties.getCodigoAtualizaJogadores());

        List<Logs> logs = consultaLogs.consulta(jobProperties.getCodigoAtualizaJogadores(), LocalDate.now());

        validaSeJaOcorreuExecucaoParaHorario(parametros, logs);

        List<Clubes> clubes = consultaClubes.consulta(Fornecedor.RAPIDAPI.getId());

        validaSeHaClubesParaBuscarJogadores(clubes);

        List<Jogadores> jogadores = consultaJogadores.consulta(clubes);

        validaSeHaJogadoresParaAtualizar(jogadores);

        salvaJogadores.salva(jogadores);
    }

    private void validaSeJaOcorreuExecucaoParaHorario(Parametros parametros, List<Logs> logs) {
        if (parametros.getHorarios().stream()
                .anyMatch(hora -> logs.stream()
                        .anyMatch(value -> value.getDataHoraExecucao().getHour() == hora.getHour())))
            throw new HorarioJaExecucaoException();
    }

    private void validaSeHaClubesParaBuscarJogadores(List<Clubes> clubes) {
        if (clubes.isEmpty()) throw new ClubesNaoLocalizadosException();
    }

    private void validaSeHaJogadoresParaAtualizar(List<Jogadores> jogadores) {
        if (jogadores.isEmpty()) throw new JogadoresNaoLocalizadosException();
    }

}
