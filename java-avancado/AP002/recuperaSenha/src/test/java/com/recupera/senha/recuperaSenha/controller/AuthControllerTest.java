package com.recupera.senha.recuperaSenha.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.recupera.senha.recuperaSenha.dto.LoginRequest;
import com.recupera.senha.recuperaSenha.dto.LoginResponse;
import com.recupera.senha.recuperaSenha.dto.RegisterRequest;
import com.recupera.senha.recuperaSenha.dto.RegisterResponse;
import com.recupera.senha.recuperaSenha.service.AuthenticationService;

class AuthControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
    }

//    @Test
//    void testRegister_Success() {
//        // Given
//        RegisterRequest request = new RegisterRequest("username", "email", "password");
//        when(authenticationService.register(any(RegisterRequest.class))).thenReturn(new RegisterResponse("Usuário cadastrado com sucesso."));
//
//        // When
//        ResponseEntity<RegisterResponse> response = authController.register(request);
//
//        // Then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals("Usuário cadastrado com sucesso.", response.getBody().message());
//    }

    @Test
    void testLogin_Success() {
        // Given
        LoginRequest request = new LoginRequest("username", "password");
        LoginResponse loginResponse = new LoginResponse("token");
        when(authenticationService.login(any(LoginRequest.class))).thenReturn(loginResponse);

        // When
        ResponseEntity<LoginResponse> response = authController.login(request);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("token", response.getBody().token());
    }
}