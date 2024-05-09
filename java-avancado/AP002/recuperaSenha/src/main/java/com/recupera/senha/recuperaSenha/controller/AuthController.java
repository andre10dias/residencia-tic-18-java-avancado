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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
	
	private final AuthenticationService authenticationService;
	
	@PostMapping("/register")
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
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
		log.info("Dados login: username:"+request.username()+"  password:"+request.password());
		
		try {
			return ResponseEntity.ok(authenticationService.login(request));
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/recover-password")
	public ResponseEntity<RecoverResponse> recoverPassword(@RequestBody @Valid RecoverRequest request) {
		try {
			return ResponseEntity.ok(authenticationService.recoverPassword(request));
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/change-password")
	public ResponseEntity<ChangePasswordResponse> changePassword(@RequestBody @Valid ChangePasswordRequeste request) {
		try {
			return ResponseEntity.ok(authenticationService.changePassword(request));
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
