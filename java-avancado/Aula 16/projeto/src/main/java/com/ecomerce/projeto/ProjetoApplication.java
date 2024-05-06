package com.ecomerce.projeto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableCaching
@SpringBootApplication
public class ProjetoApplication {

	public static void main(String[] args) {
		log.info("Iniciando aplicação...");
		SpringApplication.run(ProjetoApplication.class, args);
	}

}
