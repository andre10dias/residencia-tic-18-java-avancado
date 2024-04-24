package com.ecomerce.projeto.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecomerce.projeto.model.Usuario;
import com.ecomerce.projeto.repository.UsuarioRepository;

@Service
@Primary
@Qualifier("v1")
public class UsuarioServiceV1 {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Cacheable("usuarios")
	public Page<Usuario> listarUsuarios(Pageable pageable) {
		return repository.findByAtivoTrueOrAtivoNull(pageable);
	}
	
	public Usuario cadastrar(Usuario u) {
		return repository.save(u);
	}
	
	public Optional<Usuario> pegarPorId(Long id) {
		return repository.findById(id);
	}
	
	public Optional<Usuario> atualizar(Long id, Usuario u) {
		return repository.findById(id).map(usuario -> {
			usuario.setEmail(u.getEmail());
			usuario.setSenha(u.getSenha());
            return repository.save(usuario);
        });
	}
	
	public void remover(Long id) {
		repository.deleteById(id);
	}
	
}
