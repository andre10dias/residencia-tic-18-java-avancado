package com.recupera.senha.recuperaSenha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recupera.senha.recuperaSenha.component.EmailComponent;
import com.recupera.senha.recuperaSenha.model.Usuario;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailService {
	
	@Autowired
	private EmailComponent email;
	
	public String sendRecoveryEmail(Usuario user, String token) {
		String to = user.getEmail();
		String subject = "Recuperação de senha";
		String body = "Olá " + user.getUsername() + ",\n\n" +
		"Clique no link abaixo para redefinir sua senha:\n\n" +
		"http://localhost:8080/change-password?token=" + token;
		
		try {
			return email.sendEmail(to, subject, body);
		}
		catch(Exception e) {
			throw new RuntimeException("Erro ao enviar email: " + e.getMessage());
		}
		
	}
}
