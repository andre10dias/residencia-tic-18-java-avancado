package com.ecomerce.projeto.controller.form;

import java.util.Set;

import com.ecomerce.projeto.model.Role;
import com.ecomerce.projeto.model.Usuario;
import com.ecomerce.projeto.repository.UsuarioRepository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioForm {
	
	private String email;
	private String senha;
	private Set<Role> roles;
	private Boolean ativo;
	
	public Usuario toUsuario() {
		return new Usuario(null, email, senha, roles, ativo);
	}
	
	public Usuario atualizar(Long id, UsuarioRepository repository) {
		Usuario usuario = repository.getReferenceById(id);
		usuario.setEmail(this.email);
		usuario.setSenha(this.senha);
		
		return usuario;
	}

}
