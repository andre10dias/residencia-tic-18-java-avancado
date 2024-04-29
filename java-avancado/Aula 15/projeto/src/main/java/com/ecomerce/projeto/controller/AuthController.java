package com.ecomerce.projeto.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecomerce.projeto.controller.dto.LoginRequest;
import com.ecomerce.projeto.controller.dto.LoginResponse;
import com.ecomerce.projeto.controller.dto.RegisterRequest;
import com.ecomerce.projeto.controller.dto.RegisterResponse;
import com.ecomerce.projeto.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest registerRequest) {
        authenticationService.register(registerRequest);
        return new RegisterResponse("User registered successfully");
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return authenticationService.login(loginRequest);
    }

}
