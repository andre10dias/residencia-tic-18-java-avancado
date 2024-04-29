package com.ecomerce.projeto.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecomerce.projeto.controller.dto.LoginRequest;
import com.ecomerce.projeto.controller.dto.LoginResponse;
import com.ecomerce.projeto.controller.dto.RegisterRequest;
import com.ecomerce.projeto.mapper.UserMapper;
import com.ecomerce.projeto.model.Usuario;
import com.ecomerce.projeto.repository.UserRepository;
import com.ecomerce.projeto.security.JwtProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final UserRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserMapper usuarioMapper;

    public void register(RegisterRequest registerRequest) {
        Usuario usuario = usuarioMapper.fromRegisterRequest(registerRequest);// Usando o UserMapper para criar o objeto User
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.saveUsuario(usuario);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(),
                loginRequest.senha()));
        String token = jwtProvider.generateToken(authenticate);
        return new LoginResponse(token);
    }

}
