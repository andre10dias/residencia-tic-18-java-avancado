package com.recupera.senha.recuperaSenha.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.javafaker.Faker;
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

class AuthenticationServiceTest {

    private AuthenticationService authenticationService;
    private UsuarioRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;
    private EmailService emailService;
    private UsuarioMapper userMapper;
    private Faker faker;

    @BeforeEach
    void setUp() {
    	faker = new Faker();
        userRepository = mock(UsuarioRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        authenticationManager = mock(AuthenticationManager.class);
        jwtProvider = mock(JwtProvider.class);
        emailService = mock(EmailService.class);
        userMapper = mock(UsuarioMapper.class);
        authenticationService = new AuthenticationService(
            userRepository, passwordEncoder, authenticationManager,
            jwtProvider, userMapper, emailService
        );
    }
    
    private Usuario usuarioFaker() {
		Usuario usuarioFake = new Usuario();
		usuarioFake.setId(faker.number().randomNumber());
		usuarioFake.setUsername(faker.name().username());
		usuarioFake.setEmail(faker.internet().emailAddress());
		usuarioFake.setPassword(faker.internet().password(8, 10, true, true, true));
		
		return usuarioFake;
	}

    @Test
    void testRegister() {
        // Given
    	Usuario user = usuarioFaker();
        RegisterRequest request = new RegisterRequest(user.getUsername(), user.getEmail(), user.getPassword());
        when(userMapper.fromRegisterRequest(request)).thenReturn(user);
        
        user.setPassword(passwordEncoder.encode(user.getPassword())); 

        // When
        authenticationService.register(request);

        // Then
        verify(passwordEncoder).encode(user.getPassword());
        verify(userRepository).saveUsuario(user);
    }

    @Test
    void testLogin() {
        // Given
    	Usuario user = usuarioFaker();
        LoginRequest request = new LoginRequest(user.getUsername(), user.getPassword());
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn("principal");
        when(jwtProvider.generateToken(authentication)).thenReturn("token");

        // When
        LoginResponse response = authenticationService.login(request);

        // Then
        assertEquals("token", response.token());
    }

    @Test
    void testRecoverPassword() {
        // Given
    	Usuario user = usuarioFaker();
        RecoverRequest request = new RecoverRequest("email");
        when(userRepository.findByEmail("email")).thenReturn(java.util.Optional.ofNullable(user));
        when(jwtProvider.generateToken(user)).thenReturn("token");
        when(emailService.sendRecoveryEmail(user, "token")).thenReturn("email sent");

        // When
        RecoverResponse response = authenticationService.recoverPassword(request);

        // Then
        assertEquals("email sent", response.token());
    }

    @Test
    void testChangePassword() {
        // Given
    	Usuario user = usuarioFaker();
        ChangePasswordRequeste request = new ChangePasswordRequeste("token", "newPassword");
        when(userRepository.findByToken("token")).thenReturn(java.util.Optional.ofNullable(user));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");

        // When
        ChangePasswordResponse response = authenticationService.changePassword(request);

        // Then
        assertEquals("Senha alterada com sucesso.", response.message());
        assertNull(user.getToken());
        assertEquals("encodedPassword", user.getPassword());
    }

    @Test
    void testChangePasswordInvalidToken() {
        // Given
        ChangePasswordRequeste request = new ChangePasswordRequeste("invalidToken", "newPassword");
        when(userRepository.findByToken("invalidToken")).thenReturn(java.util.Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> authenticationService.changePassword(request));
    }
}
