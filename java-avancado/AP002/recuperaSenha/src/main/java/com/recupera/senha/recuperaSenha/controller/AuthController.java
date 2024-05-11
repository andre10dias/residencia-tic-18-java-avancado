package com.recupera.senha.recuperaSenha.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recupera.senha.recuperaSenha.dto.ChangePasswordRequeste;
import com.recupera.senha.recuperaSenha.dto.ChangePasswordResponse;
import com.recupera.senha.recuperaSenha.dto.LoginRequest;
import com.recupera.senha.recuperaSenha.dto.LoginResponse;
import com.recupera.senha.recuperaSenha.dto.RecoverRequest;
import com.recupera.senha.recuperaSenha.dto.RecoverResponse;
import com.recupera.senha.recuperaSenha.dto.RegisterRequest;
import com.recupera.senha.recuperaSenha.dto.RegisterResponse;
import com.recupera.senha.recuperaSenha.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/api/auth", produces = {"application/json"})
@Tag(name = "Recupera Senha")
public class AuthController {
	
	private final AuthenticationService authenticationService;
	
	@Operation(summary = "Realiza o cadastro de usuários.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos."),
            @ApiResponse(responseCode = "500", description = "Falha ao cadastrar usuário."),
    })
	@PostMapping(value = "/register", consumes = {"application/json"})
	public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) {
		log.info("Usuário cadastrado: username:"+request.username()+"  password:"+request.password());
		
		try {
			authenticationService.register(request);
			return ResponseEntity.ok(new RegisterResponse("Usuário cadastrado com sucesso."));
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@Operation(summary = "Realiza o login do usuário.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos."),
            @ApiResponse(responseCode = "401", description = "Não autorizado."),
            @ApiResponse(responseCode = "500", description = "Falha ao realizar login."),
    })
	@PostMapping(value = "/login", consumes = {"application/json"})
	public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
		log.info("Dados login: username:"+request.username()+"  password:"+request.password());
		
		try {
			return ResponseEntity.ok(authenticationService.login(request));
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@Operation(summary = "Recupera a senha do usuário através do envio de e-mail.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "E-mail enviado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos."),
            @ApiResponse(responseCode = "500", description = "Falha ao enviar e-mail."),
    })
	@PostMapping(value = "/recover-password", consumes = {"application/json"})
	public ResponseEntity<RecoverResponse> recoverPassword(@RequestBody @Valid RecoverRequest request) {
		try {
			return ResponseEntity.ok(authenticationService.recoverPassword(request));
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@Operation(summary = "Realiza a troca de senha do usuário.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos."),
            @ApiResponse(responseCode = "401", description = "Não autorizado."),
            @ApiResponse(responseCode = "500", description = "Falha ao trocar a senha."),
    })
	@PostMapping(value = "/change-password", consumes = {"application/json"})
	public ResponseEntity<ChangePasswordResponse> changePassword(@RequestBody @Valid ChangePasswordRequeste request) {
		try {
			return ResponseEntity.ok(authenticationService.changePassword(request));
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
