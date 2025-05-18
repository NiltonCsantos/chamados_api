package com.api.chamados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableFeignClients
public class ChamadosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChamadosApplication.class, args);
	}

}
