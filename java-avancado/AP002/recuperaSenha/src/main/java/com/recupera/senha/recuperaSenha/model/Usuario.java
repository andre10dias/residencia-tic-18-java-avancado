package com.recupera.senha.recuperaSenha.model;

import com.recupera.senha.recuperaSenha.validation.ValidPassword;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "O campo username é obrigatório.")
	@Size(min = 4, max = 20, message = "O campo username deve ter entre 4 e 20 caracteres.")
	@Column(unique = true, nullable = false)
	private String username;
	
	@NotEmpty(message = "O campo e-mail é obrigatório.")
	@Email(message = "E-mail inválido.")
	@Column(unique = true, nullable = false)
	private String email;
	
	@ValidPassword
	private String password;
	
	private String role;
	
	@Column(columnDefinition = "TEXT")
	private String token;

}
