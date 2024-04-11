package com.ecomerce.projeto.controller.dto;

import com.ecomerce.projeto.model.Usuario;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioDTO {
	
	private Long id;
	
	@NotNull
    @Column(unique = true)
    private String email;
    
    @NotNull
    @Size(min = 8, message = "A senha deve ter ao menos 6 caracteres.")
    private String senha;
    
    public UsuarioDTO(Usuario usuario) {
    	this.email = usuario.getEmail();
    	this.senha = usuario.getSenha();
    }

}
