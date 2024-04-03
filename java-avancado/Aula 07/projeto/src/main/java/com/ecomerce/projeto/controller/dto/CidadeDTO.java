package com.ecomerce.projeto.controller.dto;

import org.hibernate.validator.constraints.Length;

import com.ecomerce.projeto.model.Cidade;
import com.ecomerce.projeto.model.Estado;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CidadeDTO {
	
	private Long id;
	
	@NotEmpty(message = "O campo Nome é obrigatório.")
	@Length(min = 3, max = 50, message = "O campo Nome deve ter entre 3 e 50 caracteres.")
	private String nome;
	
	@NotEmpty(message = "O campo Estado é obrigatório.")
	private Estado estado;
	
	public CidadeDTO(Cidade cidade) {
		this.id = cidade.getId();
		this.nome = cidade.getNome();
		this.estado = cidade.getEstado();
	}

}
