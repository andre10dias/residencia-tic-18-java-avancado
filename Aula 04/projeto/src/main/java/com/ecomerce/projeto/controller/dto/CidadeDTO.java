package com.ecomerce.projeto.controller.dto;

import com.ecomerce.projeto.model.Cidade;
import com.ecomerce.projeto.model.Estado;

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
	private String nome;
	private Estado estado;
	
	public CidadeDTO(Cidade cidade) {
		this.id = cidade.getId();
		this.nome = cidade.getNome();
		this.estado = cidade.getEstado();
	}

}
