package com.ecomerce.projeto.controller.form;

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
	
	public Usuario toUsuario() {
		return new Usuario(null, email, senha);
	}
	
	public Usuario atualizar(Long id, UsuarioRepository repository) {
		Usuario usuario = repository.getReferenceById(id);
		usuario.setEmail(this.email);
		usuario.setSenha(this.senha);
		
		return usuario;
	}

}
