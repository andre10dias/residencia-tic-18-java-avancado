package com.ecomerce.projeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecomerce.projeto.model.Usuario;
import com.ecomerce.projeto.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	public List<Usuario> listarUsuarios() {
		return repository.findAll();
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
