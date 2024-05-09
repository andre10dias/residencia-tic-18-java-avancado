package com.recupera.senha.recuperaSenha.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.recupera.senha.recuperaSenha.dto.ChangePasswordRequeste;
import com.recupera.senha.recuperaSenha.dto.ChangePasswordResponse;
import com.recupera.senha.recuperaSenha.dto.LoginRequest;
import com.recupera.senha.recuperaSenha.dto.LoginResponse;
import com.recupera.senha.recuperaSenha.dto.RecoverRequest;
import com.recupera.senha.recuperaSenha.dto.RecoverResponse;
import com.recupera.senha.recuperaSenha.dto.RegisterRequest;
import com.recupera.senha.recuperaSenha.model.Usuario;
import com.recupera.senha.recuperaSenha.security.JwtProvider;
import com.recupera.senha.recuperaSenha.usuarioRepository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationService {
	
	private final UsuarioRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	private final UsuarioMapper userMapper;
	private final EmailService emailService;
	
	public void register(RegisterRequest request) {
		Usuario user = userMapper.fromRegisterRequest(request);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.saveUsuario(user);
	}
	
	public LoginResponse login(LoginRequest request) {
		log.info("Login request: {}", request.toString());
		
		Authentication authenticate = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				request.username(),
				request.password()
			)
		);
		
		log.info("Login response authenticate: {}", authenticate.getPrincipal().toString());
		String token = jwtProvider.generateToken(authenticate);
		
		return new LoginResponse(token);
	}
	
	public RecoverResponse recoverPassword(RecoverRequest request) {
		try {
			Usuario user =  userRepository.findByEmail(request.email()).orElse(null);
			
			if(user == null) {
				return new RecoverResponse("Usuário não encontrado");
			}
			
			String token = jwtProvider.generateToken(user);
			user.setToken(token);
			userRepository.updateToken(token, user.getId());
			RecoverResponse response = new RecoverResponse(emailService.sendRecoveryEmail(user, token));
			
			return response;
		}
		catch(Exception e) {
			throw new RuntimeException("Falha ao enviar e-mail: " + e.getMessage());
		}
	}
	
	public ChangePasswordResponse changePassword(ChangePasswordRequeste request) {
		Usuario user =  userRepository.findByToken(request.token()).orElse(null);
		
		if(user == null) {
			throw new RuntimeException("Token inválido.");
		}
		else {
			user.setPassword(passwordEncoder.encode(request.newPassword()));
			userRepository.updatePassword(user.getPassword(), user.getId());
			userRepository.updateToken(null, user.getId());
			return new ChangePasswordResponse("Senha alterada com sucesso.");
		}
	}
}
