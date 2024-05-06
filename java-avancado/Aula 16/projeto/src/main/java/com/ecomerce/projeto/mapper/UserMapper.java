package com.ecomerce.projeto.mapper;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ecomerce.projeto.controller.dto.RegisterRequest;
import com.ecomerce.projeto.model.Role;
import com.ecomerce.projeto.model.Usuario;

@Service
public class UserMapper {
	
//	public Usuario fromRegisterRequest(RegisterRequest registerRequest) {
//        return Usuario.builder()
//                .email(registerRequest.email())
//                .senha(registerRequest.senha())
//                .roles("ROLE_USER")
//                .build();
//    }
	
	public Usuario fromRegisterRequest(RegisterRequest registerRequest) {
        Usuario usuario = Usuario.builder()
                .email(registerRequest.email())
                .senha(registerRequest.senha())
                .ativo(true) 
                .build();
        
        Set<Role> roles = new HashSet<>();
        Role roleUser = new Role();
        roleUser.setNome("ROLE_USER");
        roles.add(roleUser);
        usuario.setRoles(roles);
        
        return usuario;
    }

}
