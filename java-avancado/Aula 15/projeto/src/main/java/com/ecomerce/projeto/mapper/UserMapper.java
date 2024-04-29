package com.ecomerce.projeto.mapper;

import com.ecomerce.projeto.controller.dto.RegisterRequest;
import com.ecomerce.projeto.model.Usuario;

public class UserMapper {
	
	public Usuario fromRegisterRequest(RegisterRequest registerRequest) {
        return Usuario.builder()
                .email(registerRequest.email())
                .senha(registerRequest.senha())
                .role("ROLE_USER")
                .build();
    }

}
