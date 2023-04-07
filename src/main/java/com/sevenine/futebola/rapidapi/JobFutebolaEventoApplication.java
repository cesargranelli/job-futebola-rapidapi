package com.sevenine.futebola.evento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class JobFutebolaEventoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobFutebolaEventoApplication.class, args);
    }

}
