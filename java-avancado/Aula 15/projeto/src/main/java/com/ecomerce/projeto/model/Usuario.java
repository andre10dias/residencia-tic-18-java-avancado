package com.ecomerce.projeto.model;

import java.io.Serializable;

import com.ecomerce.projeto.validation.ValidPassword;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull(message = "E-mail não pode ser nulo.") // Validação no nível da aplicação
    @Email(message = "E-mail inválido.")
    @Column(unique = true, nullable = false) // Restrições a nível de banco de dados
    private String email;
	
	@ValidPassword
    private String senha;
	
	@NotNull(message = "Role não pode ser nulo.")
	private String role;
	
	@NotNull(message = "Ativo não pode ser nulo.")
    private Boolean ativo = true;

}
