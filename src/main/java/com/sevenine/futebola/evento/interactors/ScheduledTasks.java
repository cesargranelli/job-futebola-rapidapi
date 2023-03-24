package com.sevenine.futebola.evento.interactors;

import com.sevenine.futebola.evento.datasources.database.ConfiguracaoJpaRepository;
import com.sevenine.futebola.evento.datasources.database.data.ConfiguracaoData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private ConfiguracaoJpaRepository jpaRepository;

    //    @Scheduled(cron = "@hourly")
    @Scheduled(cron = "0/10 * * * * *")
    public void reportCurrentTime() {
        List<ConfiguracaoData> all = jpaRepository.findAll();

        log.info(all.get(0).getParametros());
        log.info("The time is now {}", dateFormat.format(new Date()));
    }

}
