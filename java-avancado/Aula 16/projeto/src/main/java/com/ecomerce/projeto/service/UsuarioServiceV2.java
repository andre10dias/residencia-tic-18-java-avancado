package com.ecomerce.projeto.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ecomerce.projeto.model.Usuario;
import com.ecomerce.projeto.repository.UsuarioRepository;

@Service
@Qualifier("v2")
public class UsuarioServiceV2 extends UsuarioServiceV1 {
	
	@Autowired
	UsuarioRepository repository;
	
	public Optional<Usuario> removerLogicamente(Long id) {
		return repository.findById(id).map(usuario -> {
			usuario.setAtivo(false);
            return repository.save(usuario);
        });
	}

}
