package com.recupera.senha.recuperaSenha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Recupera Senha", version = "1", description = "API desenvolvida para Logar, recuperar e trocar senhas através de envio de e-mail."))
public class RecuperaSenhaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecuperaSenhaApplication.class, args);
	}

}
