package com.recupera.senha.recuperaSenha.service;

import org.springframework.stereotype.Service;

import com.recupera.senha.recuperaSenha.dto.RegisterRequest;
import com.recupera.senha.recuperaSenha.model.Usuario;

@Service
public class UsuarioMapper {
	
	public Usuario fromRegisterRequest(RegisterRequest registerRequest) {
		return Usuario.builder()
				.email(registerRequest.email())
				.username(registerRequest.username())
				.password(registerRequest.password())
				.role("ROLE_USER")
				.build();
	}
}
